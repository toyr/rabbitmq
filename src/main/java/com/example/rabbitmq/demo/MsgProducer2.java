package com.example.rabbitmq.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * @author unisk1123
 * @Description
 * @create 2021-08-05 2:49 下午
 */
@Slf4j
@Component
public class MsgProducer2 implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        MessageProperties messageProperties = message.getMessageProperties();
        byte[] body = message.getBody();
        Class<? extends Message> aClass = message.getClass();


    }
}
