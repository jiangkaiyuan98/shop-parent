package com.shop;

import com.shop.service.impl.OrderInitRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/8 下午5:22
 */
@Component
public class InitStarter implements CommandLineRunner {
    @Autowired
    private OrderInitRedisService orderInitRedisService;
    @Override
    public void run(String... args) throws Exception {
        orderInitRedisService.initOrderGoodstoRedis();
    }
}
