package com.example.rabbitmq.demo.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author unisk1123
 * @Description
 * @create 2021-08-06 11:22 上午
 */
public class User implements Serializable {
    private Integer id;
    private String name;
    private String password;
    private Long creatTime;

    public User() {
    }

    public User(Integer id, String name, String password, Long creatTime) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.creatTime = creatTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(Long creatTime) {
        this.creatTime = creatTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", creatTime=" + creatTime +
                '}';
    }
}
