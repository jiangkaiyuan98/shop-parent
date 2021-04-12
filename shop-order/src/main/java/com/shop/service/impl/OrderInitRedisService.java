package com.shop.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.shop.service.GoodsInfoService;
import com.shop.service.OrderBaseService;
import com.shop.utils.RedisTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/8 下午5:23
 */
@Component
@Slf4j
public class OrderInitRedisService {

    @Autowired
    private RedisTool redisTool;

    @Reference
    private GoodsInfoService goodsInfoService;

    @Autowired
    private OrderBaseService orderBaseService;

    public void initOrderGoodstoRedis() {
        //goodsid,stock
        List<Map<String, Object>> goodlist = goodsInfoService.getAllGoodsInfoList();
        //goodsid,stock
        List<Map<String, Object>> newOrderStockList = orderBaseService.queryAllNewOrder();
        Map<String, Integer> stockMap=new HashMap<>();
        Map<String, Integer> newOrderStock=new HashMap<>();
        //list转Map<goodsid,stock>
        goodlist.stream().forEach(e->stockMap.put(MapUtils.getString(e,"goodsid"),MapUtils.getInteger(e,"stock")));
        newOrderStockList.stream().forEach(e->newOrderStock.put(MapUtils.getString(e,"goodsid"),MapUtils.getInteger(e,"stock")));
        for(Map<String, Object> newOrderStockmap :newOrderStockList){
            String goodsid=MapUtils.getString(newOrderStockmap,"goodsId");
            Integer stock = MapUtils.getInteger(newOrderStockmap,"stock");
            if(stockMap.containsKey(goodsid)){
                Integer MySQL_stock=MapUtils.getInteger(stockMap,goodsid);
                if(MySQL_stock-stock<0)
                    log.error("商品goodsid为："+goodsid+"的商品库存异常---------");
                stockMap.put(goodsid,MySQL_stock-stock);
            }
        }
        for(String key:stockMap.keySet()){
            redisTool.hset("goods_stock",key,MapUtils.getInteger(stockMap,key));
        }
        System.out.println("g");
    }

    public void reloadstock(){
        //重新加载
        initOrderGoodstoRedis();
    }
}
