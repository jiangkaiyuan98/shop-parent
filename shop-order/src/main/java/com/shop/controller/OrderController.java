package com.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.model.Orderinfo;
import com.shop.model.OrderinfoCuntom;
import com.shop.model.ResponseBean;
import com.shop.service.GoodsInfoService;
import com.shop.service.OrderBaseService;
import com.shop.service.OrderMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.collections.MapUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：jiangkyd
 * @description：TODO 订单基础增删改查
 * @date ：2021/4/8 上午11:22
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private OrderMainService orderMainService;

    @Reference
    private GoodsInfoService goodsInfoService;

    @RequestMapping("/testDubbo")
    public void testDubbo(){
        List<Map<String,Object>> list=goodsInfoService.getAllGoodsInfoList();
    }

    @RequestMapping(value="/queryMyOrderInfo",method = RequestMethod.GET)
    public ResponseBean queryMyOrderInfo(@RequestParam Map<String,Object> queryMap){
        ResponseBean result;
        try {
            Integer userId = MapUtils.getInteger(queryMap,"userId");
            List<Map<String,Object>> resultlist=orderBaseService.queryMyOrderInfo(userId);
            if(resultlist.size()!=0)
                result=new ResponseBean(0,"查询成功",resultlist.size(),resultlist);
            else
                result=new ResponseBean(1004,"查询失败！",resultlist.size(),resultlist);
        }catch (Exception e){
            result=new ResponseBean(1004,"查询失败！",0,e);
        }
        return result;
    }
    /**
     * @author: jky
     * @description: TODO 购物车->创建订单->预扣库存->添加到mq延迟队列等待支付->死信队列中查询订单状态->未支付还原库存
     *                                                                                    ->已支付的确认减扣库存
     * @date: 2021/4/8 下午2:09
     * @param orderinfoCuntom
     * @return com.shop.model.ResponseBean
     */
    @RequestMapping(value="/addOrder",method = RequestMethod.PUT)
    public ResponseBean addOrder(@RequestBody OrderinfoCuntom orderinfoCuntom){
        ResponseBean result;
        try {
            boolean flag=orderMainService.addNewOrder(orderinfoCuntom);
            if(flag)
                result=new ResponseBean(0,"下单成功",0,null);
            else
                result=new ResponseBean(1004,"下单失败！",0,null);
        }catch (Exception e){
            result=new ResponseBean(1004,"下单异常！",0,e);
            log.error("ERROR", "Error found: ", e);
        }
        return result;
    }
}
