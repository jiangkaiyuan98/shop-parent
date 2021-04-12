package com.shop.config;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2021/3/18 下午3:55
 */
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;

/**
 * @Author:nyb
 * @DESC: 线程池配置类
 * @Date: Created in 16:10 2020/9/17
 * @Modified By:
 */
@PropertySource("classpath:application-dev.properties")
@Configuration
@EnableAsync
@Slf4j
public class ThreadConfig {

    @Value("${thread.core.pool.size}")
    private int corePoolSize;
    @Value("${thread.max.pool.size}")
    private int maxPoolSize;
    @Value("${thread.queue.capacity}")
    private int queueCapacity;
    @Value("${thread.keep.alive.seconds}")
    private int keepAliveSeconds;


    /**
     * 执行需要依赖线程池，这里配置一个线程池
     * @return
     */

    // 当池子大小小于corePoolSize，就新建线程，并处理请求
    // 当池子大小等于corePoolSize，把请求放入workQueue(QueueCapacity)中，池子里的空闲线程就去workQueue中取任务并处理
    // 当workQueue放不下任务时，就新建线程入池，并处理请求，如果池子大小撑到了maximumPoolSize，就用RejectedExecutionHandler来做拒绝处理
    // 当池子的线程数大于corePoolSize时，多余的线程会等待keepAliveTime长时间，如果无请求可处理就自行销毁

    @Bean("getExecutor")
    public ExecutorService getExecutor() {
        log.info("ThreadConfig --- start getExecutor");

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //线程池所使用的缓冲队列
        executor.setQueueCapacity(queueCapacity);
        //设置线程名
        executor.setThreadNamePrefix("MyTest-Async");
        //设置多余线程等待的时间，单位：秒
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 初始化线程
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }

}

