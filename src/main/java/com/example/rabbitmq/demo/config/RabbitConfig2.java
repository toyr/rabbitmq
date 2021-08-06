package com.example.rabbitmq.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author unisk1123
 * @Description 测试死信队列
 * @create 2021-08-06 11:06 上午
 */
@Configuration
public class RabbitConfig2 {

    /**
     * 队列1
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("first");
    }

    /**
     * 队列2
     *
     * @return
     */
    @Bean
    public Queue queue2() {
        return new Queue("second");
    }


    /**
     * topic交换器
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("exchange");
    }

    /**
     * 绑定路由队列1
     *
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage() {
        return BindingBuilder.bind(queue()).to(topicExchange()).with("queue.first.*");
    }

    /**
     * 绑定路由队列2
     *
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("queue.second.#");
    }

    /**
     * dlx死信路由
     *
     * @return
     */
    @Bean
    public TopicExchange derictExchange() {
        return new TopicExchange("dtlExchange");
    }

    /**
     * 生产死信的队列
     *
     * @return
     */
    @Bean
    public Queue dtlQueue() {
        Map<String, Object> args = new HashMap<>();
        // 指定过期消息跳转的交换器名称
        args.put("x-dead-letter-exchange", "dtlExchange");
        // 指定跳转消息携带的routing-key
        args.put("x-dead-letter-routing-key", "dead.msg.aa");
        return QueueBuilder.durable("deadQueue").withArguments(args).build();
    }

    /**
     * 绑定 消费死信的队列 和 死信路由
     *
     * @return
     */
    @Bean
    public Binding bindingDeadExchage() {
        // routing-key 保持一致
        return BindingBuilder.bind(queue()).to(derictExchange()).with("dead.msg.#");
    }

    /**
     * 绑定 产生死信的队列和任意一个路由
     *
     * @return
     */
    @Bean
    public Binding bindingProductExchange() {
        return BindingBuilder.bind(dtlQueue()).to(topicExchange()).with("test.dead.#");
    }


    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }


}
