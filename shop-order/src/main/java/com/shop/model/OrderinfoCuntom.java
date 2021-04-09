package com.shop.model;

import java.util.Date;
import java.util.List;

public class OrderinfoCuntom extends Orderinfo{
    private List<Ordergoods> goodslist;

    public List<Ordergoods> getGoodslist() {
        return goodslist;
    }

    public void setGoodslist(List<Ordergoods> goodslist) {
        this.goodslist = goodslist;
    }
}