package com.wzy.zyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.zyapi.model.dto.UserInterfaceInfo.UserInterfaceInfoQueryRequest;
import com.wzy.zyapi.model.vo.UserInterfaceInfo.UserInterfaceInfoVo;
import com.wzy.zycommon.model.entity.UserInterfaceInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-10-11 22:05
**/

@Mapper
public interface UserInterfaceInfoMapper extends BaseMapper<UserInterfaceInfo> {


    List<UserInterfaceInfoVo> getListt(UserInterfaceInfoQueryRequest userInterfaceInfoQueryRequest);

}
