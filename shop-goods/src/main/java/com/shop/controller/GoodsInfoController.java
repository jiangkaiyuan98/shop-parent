package com.shop.controller;

import com.shop.model.ResponseBean;
import com.shop.service.GoodsInfoService;
import org.apache.commons.collections.MapUtils;
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
    public ResponseBean getGoodsInfoList(@RequestParam Map<String,Object> queryMap) {
        ResponseBean result;
        try {
            List<Map<String,Object>> resultlist=goodsInfoService.getGoodsInfoList(queryMap);
            if(resultlist.size()!=0)
                result=new ResponseBean(0,"查询成功",resultlist.size(),resultlist);
            else
                result=new ResponseBean(1004,"查询失败！",resultlist.size(),resultlist);
        }catch (Exception e){
            result=new ResponseBean(1004,"查询失败！",0,e);
        }
        return result;
    }

    @PostMapping("/addGoodsInfo")
    public void addGoodsInfo(@RequestBody Map<String,Object> queryMap) {
        goodsInfoService.getGoodsInfoList(queryMap);
    }
}
