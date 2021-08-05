package com.example.rabbitmq.demo;

import com.example.rabbitmq.demo.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author unisk1123
 * @Description
 * @create 2020-06-09 9:40 AM
 */
@Slf4j
@Component
@RabbitListener(queues = {RabbitConfig.QUEUE_A, RabbitConfig.QUEUE_B, RabbitConfig.QUEUE_C})
public class MsgReceiver {

    @RabbitHandler
    public void process(String content) {
        log.info("接收处理队列当中的消息： " + content);
    }
}
