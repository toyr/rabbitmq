package com.example.rabbitmq.demo.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jxrt
 * @description: DLX和TTL模拟延迟队列
 * @date 2022/3/154:06 下午
 */
@Configuration
public class RabbitConfig3 {

    public static final String TTL_TOPIC_EXCHANGE = "ttl.topic.exchange";
    public static final String TTL_TOPIC_QUEUE = "ttl_topic_queue";
    public static final String TTL_TOPIC_ROUTING_KEY = "*.topic.*";
    public static final String TTL_DELAY_EXCHANGE = "ttl.dlx.exchange";
    public static final String TTL_DELAY_QUEUE = "ttl_dlk_queue";
    public static final String TTL_DELAY_ROUTING_KEY = "ttl.dlrk.rountingkey";

    /**
     * 声明队列
     *
     * @return
     */
    @Bean
    public Queue topicQueue() {
        Map<String, Object> args = new HashMap<>();
        // 设置消息发送到队列之后多久被丢弃，单位：毫秒
        args.put("x-message-ttl", 10000);
        // 消息变成死信一般有以下几种情况引起 1. 消息被拒绝，并且设置requeue参数为false 2. 信息过期 3. 队列达到最大长度
        // x-dead-letter-exchange 参数是指消息变成死信之后重新发送的DLX
        args.put("x-dead-letter-exchange", TTL_DELAY_EXCHANGE);
        // 为DLX指定路由建DLK
        args.put("x-dead-letter-routing-key", TTL_DELAY_ROUTING_KEY);
        // 定义优先级队列，消息最大优先级为15，优先级范围为0-15，数字越大优先级越高
        args.put("x-max-priority", 15);
        // 设置持久化队列
        return QueueBuilder.durable(TTL_TOPIC_QUEUE).withArguments(args).build();
    }

    /**
     * 声明Topic 交换机
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TTL_TOPIC_EXCHANGE);
    }

    /**
     * Topic 交换机和队列通过bindingkey绑定
     *
     * @return
     */
    @Bean
    public Binding bindingTopicExchangeQueue() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(TTL_TOPIC_ROUTING_KEY);
    }

    //====================延迟队列及交换机定义==========
    @Bean
    public Queue ttlQueue() {
        return QueueBuilder.durable(TTL_DELAY_QUEUE).build();
    }

    @Bean
    public TopicExchange ttlExchange() {
        return new TopicExchange(TTL_DELAY_EXCHANGE);
    }

    @Bean
    public Binding bindingTtlDirectExchangeQueue() {
        return BindingBuilder.bind(ttlQueue()).to(ttlExchange()).with(TTL_DELAY_ROUTING_KEY);
    }


}
