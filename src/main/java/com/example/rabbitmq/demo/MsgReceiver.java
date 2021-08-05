package com.example.rabbitmq.demo;

import com.example.rabbitmq.demo.config.RabbitConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author unisk1123
 * @Description
 * @create 2020-06-09 9:40 AM
 */
@Slf4j
@Component
public class MsgReceiver {


    @RabbitHandler
    @RabbitListener(queues = {RabbitConfig.QUEUE_A, RabbitConfig.QUEUE_B, RabbitConfig.QUEUE_C})
    public void process(String content) {
        log.info("接收处理队列当中的消息： " + content);
    }

   /* @RabbitListener(queues = RabbitConfig.QUEUE_A)
    @RabbitHandler
    public void process(Message message, Channel channel) throws IOException {
        log.info("接收处理队列当中的消息： " + message);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }*/
}
