package com.shop.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.shop.model.Ordergoods;
import com.shop.model.OrderinfoCuntom;
import com.shop.service.OrderBaseService;
import com.shop.service.OrderMainService;
import com.shop.utils.RedisTool;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/8 上午11:23
 */
@Service
public class OrderMainServiceImpl implements OrderMainService {
    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private RedisTool redisTool;

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
    public boolean addNewOrder(OrderinfoCuntom orderinfoCuntom) {
        List<Ordergoods> orderGoodsList = orderinfoCuntom.getGoodslist();
        for(Ordergoods ordergoods:orderGoodsList){
            if(redisTool.decrGoodsStock(GET_COUPON_CODE,ordergoods.getGoodsid().toString(),ordergoods.getGoodsnumber()))
                return false;
        }
        orderBaseService.addOrder(orderinfoCuntom);
        return true;
    }
}
