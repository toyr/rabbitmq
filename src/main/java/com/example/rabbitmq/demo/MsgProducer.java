package com.example.rabbitmq.demo;

import com.example.rabbitmq.demo.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author unisk1123
 * @Description
 * @create 2020-06-09 9:32 AM
 */
@Slf4j
@Component
public class MsgProducer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {


    private RabbitTemplate rabbitTemplate;

    @Autowired
    public MsgProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        // 设置确认模式 producer-> exchange
        rabbitTemplate.setConfirmCallback(this);
        // exchange -> queue
        rabbitTemplate.setReturnCallback(this);
    }

    public void sendMsg(String content) {
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        //把消息放入ROUTINGKEY_A对应的队列当中去，对应的是队列A
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_A, RabbitConfig.ROUTINGKEY_A, content, correlationId);

    }


    public void sendAll(String content) {
        rabbitTemplate.convertAndSend(RabbitConfig.FANOUT_EXCHANGE, "", content);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        log.info(" 回调id:" + correlationData);
        if (ack) {
            log.info("消息成功消费");
        } else {
            log.info("消息消费失败:" + cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("匹配queue失败:message: {}, replyCode: {}, replyText: {}, exchange: {}, routingKey:{}",
                message.toString(), replyCode, replyText, exchange, routingKey);
    }
}
