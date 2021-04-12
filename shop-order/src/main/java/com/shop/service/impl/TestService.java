package com.shop.service.impl;

import com.shop.model.OrderinfoCuntom;
import com.shop.service.OrderMainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.Future;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/9 下午6:46
 */
@Service
@Slf4j
public class TestService {
    @Autowired
    private OrderMainService orderMainService;
    @Async("getExecutor")
    public Future<Integer> test(){
        log.info("start"+Thread.currentThread().getName()+"-------");
        Integer count=0;
        for(int i=0;i<15;i++){
            OrderinfoCuntom orderinfoCuntom=new OrderinfoCuntom(UUID.randomUUID().toString().replace("-", "").toLowerCase());

            if(orderMainService.addNewOrder(orderinfoCuntom)) {
                log.info(Thread.currentThread().getName() + "----秒杀成功1个商品---");
                count++;
            }
            else
                log.error(Thread.currentThread().getName()+"----秒杀失败--------");
        }
        log.info("end"+Thread.currentThread().getName()+"-------");
        return new AsyncResult<>(count);
    }
}