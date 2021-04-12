package com.shop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderinfoCuntom extends Orderinfo implements Serializable {
    private List<Ordergoods> goodslist;

    public List<Ordergoods> getGoodslist() {
        return goodslist;
    }

    public void setGoodslist(List<Ordergoods> goodslist) {
        this.goodslist = goodslist;
    }

    public OrderinfoCuntom() {
    }

    public void setUUIDOrderNo(String orderNo){
        this.setOrderno(orderNo);
        for(Ordergoods ordergoods:this.goodslist){
            ordergoods.setOrderid(orderNo);
        }
    }

    public OrderinfoCuntom(String orderno) {
        OrderinfoCuntom orderinfoCuntom=new OrderinfoCuntom();
        List<Ordergoods> list=new ArrayList<>();
        Ordergoods ordergoods=new Ordergoods();
        this.setOrderno(orderno);
        this.setOrderstate("-1");
        this.setUserid("20166007");
        this.setAmount((float) 3999);
        ordergoods.setGoodsid("123123123");
        ordergoods.setGoodsnumber(1);
        ordergoods.setSalecount(1);
        ordergoods.setOrderid(orderno);
        ordergoods.setPrice((float) 3999);
        list.add(ordergoods);
        this.goodslist=list;
    }
}