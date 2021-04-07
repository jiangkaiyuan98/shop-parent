package com.shop.service.impl;

import com.shop.mapper.GoodsinfoMapper;
import com.shop.service.GoodsInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/7 上午9:39
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {
    @Autowired
    private GoodsinfoMapper goodsinfoMapper;

    @Override
    public List<Map<String, Object>> getGoodsInfoList(Map<String, Object> queryMap) {
        return goodsinfoMapper.selectGoodsInfoList();
    }
}
