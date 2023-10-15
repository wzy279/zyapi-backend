package com.wzy.zyapi.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-13 08:36
 **/
@SpringBootTest
class UserInterfaceInfoServiceTest {

    @Resource
    private UserInterfaceInfoService userInterfaceInfoService;
    @Test
    void validCount() {
        boolean validCount = userInterfaceInfoService.validCount(16, 15);
        System.out.println(validCount);
    }
}
