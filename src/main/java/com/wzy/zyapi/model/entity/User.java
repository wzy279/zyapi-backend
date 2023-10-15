package com.wzy.zyapi.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 *
 * @description
 * @author: Wangzhaoyi
 */
@ApiModel(description="用户")
@Data
@TableName(value = "`user`")
public class User implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="id")
    private Long id;

    /**
     * 账号
     */
    @TableField(value = "user_account")
    @ApiModelProperty(value="账号")
    private String userAccount;

    /**
     * 密码
     */
    @TableField(value = "user_password")
    @ApiModelProperty(value="密码")
    private String userPassword;

    /**
     * 微信开放平台id
     */
    @TableField(value = "union_id")
    @ApiModelProperty(value="微信开放平台id")
    private String unionId;

    /**
     * 公众号openId
     */
    @TableField(value = "mp_openId")
    @ApiModelProperty(value="公众号openId")
    private String mpOpenid;

    /**
     * 用户昵称
     */
    @TableField(value = "user_name")
    @ApiModelProperty(value="用户昵称")
    private String userName;

    /**
     * 用户头像
     */
    @TableField(value = "user_avatar")
    @ApiModelProperty(value="用户头像")
    private String userAvatar;

    /**
     * 用户简介
     */
    @TableField(value = "user_profile")
    @ApiModelProperty(value="用户简介")
    private String userProfile;

    /**
     * 用户角色：user/admin/ban
     */
    @TableField(value = "user_role")
    @ApiModelProperty(value="用户角色：user/admin/ban")
    private String userRole;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value="是否删除")
    @TableLogic
    private Integer isDelete;

    @TableField(value = "accesskey")
    private String accesskey;

    @TableField(value = "secretkey")
    private String secretkey;


    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
