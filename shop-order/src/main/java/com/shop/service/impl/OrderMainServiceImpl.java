package com.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.shop.config.RabbitConfig;
import com.shop.model.Ordergoods;
import com.shop.model.Orderinfo;
import com.shop.model.OrderinfoCuntom;
import com.shop.service.OrderBaseService;
import com.shop.service.OrderMainService;
import com.shop.utils.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/8 上午11:23
 */
@Service
@Slf4j
public class OrderMainServiceImpl implements OrderMainService {
    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private RedisTool redisTool;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static final String GET_COUPON_CODE ="local stock_values = redis.call('hget','goods_stock',KEYS[1]); \n" +
            " if tonumber(stock_values) < math.abs(tonumber(ARGV[1])) then \n return 0; \n else \n redis.call('hincrby','goods_stock',KEYS[1],tonumber(ARGV[1])); \n return 1; \n end";
    /**
     * @author: jky
     * @description: TODO
     *         在Redis中查看库存，预扣库存
     *         扣除成功，将订单信息写入到数据库中（还是写入到redis中，等支付成功后在写入库（redis挂了之后会导致一些问题---不使用））
     *         放入消息队列中（未支付订单及时被处理），等待用户支付
     * @date: 2021/4/9 上午9:38
     * @param orderinfoCuntom
     * @return boolean
     */
    @Override
    public boolean addNewSecKillOrder(OrderinfoCuntom orderinfoCuntom) {
        List<Ordergoods> orderGoodsList = orderinfoCuntom.getGoodslist();
        for(Ordergoods ordergoods:orderGoodsList){
            if(redisTool.decrGoodsStock(GET_COUPON_CODE,ordergoods.getGoodsid(),ordergoods.getGoodsnumber()))
                return false;
        }
        orderBaseService.addOrder(orderinfoCuntom);

        log.info("【订单生成时间】" + new Date().toString() +"【1分钟后检查订单是否已经支付】" + orderinfoCuntom.toString() );

        String orderinfoCuntomJson = JSON.toJSONString(orderinfoCuntom);

        this.rabbitTemplate.convertAndSend(RabbitConfig.ORDER_DELAY_EXCHANGE, RabbitConfig.ORDER_DELAY_ROUTING_KEY, orderinfoCuntomJson, message -> {
            // 如果配置了 params.put("x-message-ttl", 5 * 1000); 那么这一句也可以省略,具体根据业务需要是声明 Queue 的时候就指定好延迟时间还是在发送自己控制时间
            //设置消息过期时间
            message.getMessageProperties().setExpiration(1 * 1000 * 60 + "");
            return message;
        });

        return true;
    }

    @RabbitListener(queues = {RabbitConfig.ORDER_QUEUE_NAME},concurrency = "10",containerFactory = "mqQosListener")
    public void orderDelayQueue(String  orderinfoCuntomJson, Message message, Channel channel) throws Exception {
        if(redisTool.sHasKey(RabbitConfig.ORDER_QUEUE_NAME,message.getMessageProperties().getMessageId())){
            System.out.print(message.getMessageProperties().getConsumerTag());
            System.out.println("消息已经被消费 message="+message.toString());
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            return ;
        }
        try {
            log.info("###########################################");
            log.info("【orderDelayQueue 监听的消息】 - 【消费时间】 - [{}]- 【订单号】 - [{}]", new Date(), orderinfoCuntomJson);
            //json转为对象
            OrderinfoCuntom orderinfoCuntom = JSON.parseObject(orderinfoCuntomJson, OrderinfoCuntom.class);
            String orderNewState=orderBaseService.queryOrderInfoByOrderNo(orderinfoCuntom.getOrderno());

            if ("-1".equals(orderNewState)) {
                orderinfoCuntom.setOrderstate("2");
                orderBaseService.updateOrderState(orderinfoCuntom);
                log.info("【该秒杀订单未支付，取消订单，还原库存】" + orderinfoCuntom.getOrderno());
                for (Ordergoods ordergoods:orderinfoCuntom.getGoodslist()){
                    //还原库存
                    redisTool.hincr("goods_stock",ordergoods.getGoodsid(),ordergoods.getGoodsnumber());
                }
            } else if ("0".equals(orderNewState)) {
                log.info("【该订单未完成支付自动取消】");
                orderinfoCuntom.setOrderstate("2");
                orderBaseService.updateOrderState(orderinfoCuntom);
            }else if ("1".equals(orderNewState)) {
                log.info("【该订单已完成支付】");
            } else if ("2".equals(orderNewState)) {
                log.info("【该订单已取消】");
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            System.out.println("消费成功，返回ack删除消息");
        } catch (IOException e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            redisTool.setRemove(RabbitConfig.ORDER_QUEUE_NAME,message.getMessageProperties().getMessageId());
            log.error("处理时发生异常，删除redis的messageid，消息重发");
            e.printStackTrace();
        }

    }

}
