package com.shop.service;

import java.util.List;
import java.util.Map;

public interface GoodsInfoService {
    List<Map<String, Object>> getGoodsInfoList(Map<String, Object> queryMap);
}
