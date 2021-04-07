package com.shop.controller;

import com.shop.service.GoodsInfoService;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/7 上午11:16
 */
@RestController
@RequestMapping("/goods")
public class GoodsInfoController {
    @Autowired
    private GoodsInfoService goodsInfoService;

    @RequestMapping("/getGoodsInfoList")
    public void getGoodsInfoList(@RequestParam Map<String,Object> queryMap) {
        List<Map<String,Object>> goodsList=goodsInfoService.getGoodsInfoList(queryMap);
    }

    @PostMapping("/addGoodsInfo")
    public void addGoodsInfo(@RequestBody Map<String,Object> queryMap) {
        goodsInfoService.getGoodsInfoList(queryMap);
    }
}
