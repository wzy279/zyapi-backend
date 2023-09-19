package com.wzy.zyapi.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wzy.zyapi.model.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户数据库操作
 *
 * @description
 * @author: Wangzhaoyi
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




