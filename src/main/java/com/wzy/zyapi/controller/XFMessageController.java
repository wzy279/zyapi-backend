package com.wzy.zyapi.controller;

import com.wzy.zyapi.common.BaseResponse;
import com.wzy.zyapi.service.PushService;
import com.wzy.zyapi.service.UserService;
import com.wzy.zycommon.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-04 10:50
 **/
@Slf4j
@RestController
@RequestMapping("/xfModel")
public class XFMessageController {

    @Autowired
    private PushService pushService;

    @Resource
    private UserService userService;

    @GetMapping("/test")
    public BaseResponse<String> test(Long id,String text, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return pushService.pushMessageToXFServer(id,String.valueOf(loginUser.getId()), text);
    }
}
