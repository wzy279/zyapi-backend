package com.wzy.zyapi.mapper;

import com.wzy.zycommon.model.entity.InterfaceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-09-23 18:09
 **/
@SpringBootTest
public class InterfaceMapperTest {
    @Autowired
    InterfaceInfoMapper interfaceInfoMapper;


    @Test
    void getList(){
        List<InterfaceInfo> interfaceInfos = interfaceInfoMapper.selectList(null);
        System.out.println(interfaceInfos);
    }
}
