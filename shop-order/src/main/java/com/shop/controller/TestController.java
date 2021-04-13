package com.shop.controller;

import com.shop.model.Ordergoods;
import com.shop.model.OrderinfoCuntom;
import com.shop.service.impl.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/4/9 下午6:39
 */
@RestController
public class TestController {
    @Autowired
    private TestService testService;


    @RequestMapping("/testOrder")
    public Integer testOrder() throws InterruptedException, ExecutionException {
        List<Future<Integer>> futurepool=new ArrayList<>();
        for(int i=0;i<10;i++){
            Future<Integer> future=testService.test();
            futurepool.add(future);
        }
        Thread.sleep(10000);
        Integer result=0;
        while(futurepool.size()>0){
                Iterator<Future<Integer>> iterable = futurepool.iterator();

                //遍历一遍
                while(iterable.hasNext()){
                        Future<Integer> future = iterable.next();
                        //如果任务完成取结果，否则判断下一个任务是否完成
                        if (future.isDone() && !future.isCancelled()){
                                //获取结果
                                Integer i = future.get();
                                System.out.println("任务i=" + i + "获取完成，移出任务队列！");
                                result +=i;
                                //任务完成移除任务
                                iterable.remove();
                            }else{
                                Thread.sleep(1);//避免CPU高速运转，这里休息1毫秒，CPU纳秒级别
                            }
                    }
            }
        return result;
    }
    @RequestMapping("/testOrder2")
    public Integer testOrder2() throws InterruptedException, ExecutionException {
        final CountDownLatch latch = new CountDownLatch(200);

        List<Future<Integer>> futurepool=new ArrayList<>();
        for(int i=1;i<=1000;i++){
            Future<Integer> future=testService.test2(latch);
            futurepool.add(future);
            if(i%200==0){
                try {
                    latch.await();//等待CountDownLatch  为0并发执行
                } catch (InterruptedException e) {
                }
            }
        }

        Integer result=0;
        while(futurepool.size()>0){
            Iterator<Future<Integer>> iterable = futurepool.iterator();

            //遍历一遍
            while(iterable.hasNext()){
                Future<Integer> future = iterable.next();
                //如果任务完成取结果，否则判断下一个任务是否完成
                if (future.isDone() && !future.isCancelled()){
                    //获取结果
                    Integer i = future.get();
                    System.out.println("任务i=" + i + "获取完成，移出任务队列！");
                    result +=i;
                    //任务完成移除任务
                    iterable.remove();
                }else{
                    Thread.sleep(1);//避免CPU高速运转，这里休息1毫秒，CPU纳秒级别
                }
            }
        }
        return result;
    }
}
