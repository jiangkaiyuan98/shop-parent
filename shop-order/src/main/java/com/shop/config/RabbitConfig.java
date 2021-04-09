package com.shop.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author ：jiangkyd
 * @description：TODO
 * @date ：2020/11/5 10:00 上午
 */
@Configuration
@PropertySource("classpath:rabbitmq.properties")
public class RabbitConfig {
    @Value("${firstqueue}")
    private String firstQueue;
    @Value("${secondqueue}")
    private String secondQueue;
    @Value("${qosqueue}")
    private  String qosQueue;

    @Value("${fanoutexchange}")
    private String fanoutExchange;

    @Value("${qosfanoutexchange}")
    private String qosfanoutExchange;

    //创建队列
    @Bean("FirstQueue")
    public Queue getFirstQueue(){
        return new Queue(firstQueue);
    }
    @Bean("SecondQueue")
    public Queue getSecondQueue(){
        return new Queue(secondQueue);
    }
    @Bean("QosQueue")
    public Queue getQosQueue(){
        return new Queue(qosQueue);
    }

    @Bean("FanoutExchange")
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange(fanoutExchange);
    }

    @Bean("QosFanoutExchange")
    public FanoutExchange getQosFanoutExchange(){
        return new FanoutExchange(qosfanoutExchange);
    }
    //绑定广播类型交换机
    @Bean
    public Binding bindFirst(@Qualifier("FirstQueue")Queue queue,@Qualifier("FanoutExchange")FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }
    @Bean
    public Binding bindSecond(@Qualifier("SecondQueue")Queue queue,@Qualifier("FanoutExchange")FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }
    @Bean
    public Binding bindQos(@Qualifier("QosQueue")Queue queue,@Qualifier("QosFanoutExchange")FanoutExchange exchange){
        return BindingBuilder.bind(queue).to(exchange);
    }
    /**
     * 在消费端转换JSON消息
     * 监听类都要加上containerFactory属性
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        Jackson2JsonMessageConverter jsonMessageConverter=new Jackson2JsonMessageConverter();
        jsonMessageConverter.setCreateMessageIds(true);
        factory.setMessageConverter(jsonMessageConverter);
        //手动ack
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        factory.setAutoStartup(true);
        return factory;
    }

    @Bean(name="mqQosListener")
    public SimpleRabbitListenerContainerFactory mqQosListener(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        Jackson2JsonMessageConverter jsonMessageConverter=new Jackson2JsonMessageConverter();
        jsonMessageConverter.setCreateMessageIds(true);
        factory.setMessageConverter(jsonMessageConverter);
        //手动ack
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置限流
        factory.setPrefetchCount(2);
//        factory.setAutoStartup(true);
        return factory;
    }

    //批量监听
    @Bean("batchQueueRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory batchQueueRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //设置批量
        factory.setBatchListener(true);
        factory.setConsumerBatchEnabled(true);//设置BatchMessageListener生效
        factory.setBatchSize(10);//设置监听器一次批量处理的消息数量
        return factory;
    }

}
