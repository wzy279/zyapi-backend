package com.wzy.zyapi.mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.zycommon.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据库操作
 *
 * @description
 * @author: Wangzhaoyi
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    String selectUserAccountById(@Param("id")Long id);



}




