package com.example.rabbitmq.demo;

import com.example.rabbitmq.demo.config.RabbitConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author unisk1123
 * @Description
 * @create 2020-06-09 9:43 AM
 */
@Slf4j
@RabbitListener(queues = RabbitConfig.QUEUE_A)
public class MsgReceiverC_one {

    @RabbitHandler
    public void process(String content) {
        log.info("处理器one接收处理队列A当中的消息： " + content);
    }
}
