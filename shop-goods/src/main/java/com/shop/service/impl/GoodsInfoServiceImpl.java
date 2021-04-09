package com.shop.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.shop.mapper.GoodsinfoMapper;
import com.shop.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/7 上午9:39
 */
@Service(interfaceClass = GoodsInfoService.class)
@Component
public class GoodsInfoServiceImpl implements GoodsInfoService {
    @Autowired
    private GoodsinfoMapper goodsinfoMapper;

    @Override
    public List<Map<String, Object>> getGoodsInfoList(Map<String, Object> queryMap) {
        return goodsinfoMapper.selectGoodsInfoList();
    }

    @Override
    public List<Map<String, Object>> getAllGoodsInfoList() {
        return goodsinfoMapper.selectGoodsInfoList();
    }
}
