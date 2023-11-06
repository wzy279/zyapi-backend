package com.wzy.wzyapiinterface.controller;

import cn.hutool.json.JSONUtil;
import com.wzy.wzyapiinterface.model.User;
import com.wzy.wzyapiinterface.util.SignUtil;
import org.springframework.web.HttpRequestHandler;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-01 15:02
 **/
@RestController
@RequestMapping("/name")
public class Users {
    @GetMapping("/get")
    public String getname(String name){
        return "get name is "+name;
    }
    @PostMapping("post")
    public String getnamepost(@RequestParam String name){
        return "postparam name is "+name;
    }

    @PostMapping("post2")
    public String getnamepost2(@RequestBody User user, HttpServletRequest request){
        String Json = JSONUtil.formatJsonStr(String.valueOf(user));
        System.out.println("User is" +user);
        return "postbody name is "+user.getName();
    }


}
