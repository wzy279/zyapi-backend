package com.wzy.zyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.zycommon.model.entity.InterfaceInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-09-18 19:16
**/
@Mapper
public interface InterfaceInfoMapper extends BaseMapper<InterfaceInfo> {

    String selectNameById(@Param("id")Long id);


    List<InterfaceInfo> queryAllByUpdateTime(@Param("updateTime")Date updateTime);





}
