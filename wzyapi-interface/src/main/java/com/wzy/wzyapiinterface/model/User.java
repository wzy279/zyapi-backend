package com.wzy.wzyapiinterface.model;

import lombok.Data;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-02 12:41
 **/
@Data
public class User {
    private String name;
    private String accessKey;
    private String secretKey;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", accessKey='" + accessKey + '\'' +
                ", secretKey='" + secretKey + '\'' +
                '}';
    }
}
