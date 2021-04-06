package com.jky.shopgoods;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubboConfiguration
public class ShopGoodsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopGoodsApplication.class, args);
    }

}
