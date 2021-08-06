package com.example.rabbitmq.demo.local;

import com.example.rabbitmq.demo.domain.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author unisk1123
 * @Description
 * @create 2021-08-06 11:28 上午
 */
@Component
public class Recept {

    @RabbitListener(queues = "first")
    @RabbitHandler
    public void recept(User user) {
        System.out.println("延迟队列消费时间：" + (System.currentTimeMillis() - user.getCreatTime()) + "ms");

    }
}
