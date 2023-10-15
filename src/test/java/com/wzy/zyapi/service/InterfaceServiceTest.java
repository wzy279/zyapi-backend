package com.wzy.zyapi.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wzy.zyapi.model.entity.InterfaceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-09-23 18:11
 **/
@SpringBootTest
public class InterfaceServiceTest {


    @Autowired
    InterfaceInfoService interfaceInfoService;


    @Test
    void getList(){
        Page<InterfaceInfo> page = interfaceInfoService.page(new Page<>(1, 2), null);
        System.out.println(page.getRecords());
    }

}
