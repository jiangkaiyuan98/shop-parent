package com.shop.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shop.mapper.OrdergoodsMapper;
import com.shop.mapper.OrderinfoMapper;
import com.shop.model.Ordergoods;
import com.shop.model.Orderinfo;
import com.shop.model.OrderinfoCuntom;
import com.shop.service.OrderBaseService;
import com.shop.utils.RedisTool;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/8 上午11:23
 */
@Service
public class OrderBaseServiceImpl implements OrderBaseService {

    @Autowired
    private RedisTool redisTool;

    @Autowired
    private OrderinfoMapper orderinfoMapper;

    @Autowired
    private OrdergoodsMapper ordergoodsMapper;
    @Override
    public List<Map<String, Object>> queryMyOrderInfo(Integer userId) {
        return null;
    }

    @Override
    public boolean addOrder(OrderinfoCuntom orderinfoCuntom) {
        orderinfoMapper.insert(orderinfoCuntom);
        List<Ordergoods> orderGoodsList = orderinfoCuntom.getGoodslist();
        for(Ordergoods ordergoods:orderGoodsList){
            ordergoodsMapper.insert(ordergoods);
        }
        return false;
    }

    @Override
    public List<Map<String, Object>> queryAllNewOrder() {
        List<Map<String, Object>> newOrderList = orderinfoMapper.queryAllNewOrder();
        return newOrderList;
    }
}
