package com.example.rabbitmq.demo.controller;

import com.example.rabbitmq.demo.MsgProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author unisk1123
 * @Description
 * @create 2020-06-09 9:59 AM
 */
@RestController
public class HelloController {

    @Autowired
    private MsgProducer msgProducer;

    @RequestMapping("/sendMsg")
    public String sendMsg() {
        msgProducer.sendMsg("hello");

        return "hello";

    }


    @RequestMapping("/sendAll/{content}")
    public String sendAll(@PathVariable("content") String content) {
        msgProducer.sendAll(content);

        return "hello";

    }


}
