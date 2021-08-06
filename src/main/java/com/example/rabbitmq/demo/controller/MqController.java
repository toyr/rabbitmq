package com.example.rabbitmq.demo.controller;

import com.example.rabbitmq.demo.domain.User;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author unisk1123
 * @Description
 * @create 2021-08-06 11:21 上午
 */
@RestController
public class MqController {


    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MqController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private static final String QUEUE_FIRST = "queue1";
    private static final String QUEUE_SECOND = "queue2";

    @RequestMapping(value = "/dtl/sendMsg", method = RequestMethod.GET)
    public String send(String name) {
//        long time = System.currentTimeMillis();
        User user = new User();
        user.setName("admin");
        user.setPassword("123");
        if (QUEUE_FIRST.endsWith(name)) {
            rabbitTemplate.convertAndSend("exchange", "queue.first.a", user);
        } else if (QUEUE_SECOND.endsWith(name)) {
            rabbitTemplate.convertAndSend("exchange", "queue.second.asafdas", user);
        } else {
            MessagePostProcessor postProcessor = new MessagePostProcessor() {
                @Override
                public Message postProcessMessage(Message message) throws AmqpException {
                    message.getMessageProperties().setContentEncoding("UTF-8");
                    message.getMessageProperties().setExpiration("10000");
                    return message;
                }
            };
            user.setCreatTime(System.currentTimeMillis());
            // 发送消息到指定：交换器， routing-key，消息体（支持对象），配置文件
            rabbitTemplate.convertAndSend("exchange", "test.dead.ok", user, postProcessor);
        }

        return "success";
    }
}
