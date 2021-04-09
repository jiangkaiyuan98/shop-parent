package com.shop.service;

import com.shop.model.OrderinfoCuntom;

import java.util.List;
import java.util.Map;

/**
 * @author: jky
 * @description: TODO 订单基础增删改查
 * @date: 2021/4/8 上午11:21
 * @return
 */
public interface OrderBaseService {

    List<Map<String, Object>> queryMyOrderInfo(Integer userId);

    boolean addOrder(OrderinfoCuntom orderinfoCuntom);

    List<Map<String, Object>> queryAllNewOrder();
}
