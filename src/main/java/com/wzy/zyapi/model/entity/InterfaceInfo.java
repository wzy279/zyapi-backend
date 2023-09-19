package com.wzy.zyapi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-09-18 19:16
**/
 
/**
    * 接口信息
    */
@ApiModel(description="接口信息")
@Data
@TableName(value = "interface_info")
public class InterfaceInfo {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value="描述")
    private String description;

    /**
     * 接口地址
     */
    @TableField(value = "url")
    @ApiModelProperty(value="接口地址")
    private String url;

    /**
     * 请求头
     */
    @TableField(value = "request_header")
    @ApiModelProperty(value="请求头")
    private String requestHeader;

    /**
     * 响应头
     */
    @TableField(value = "response_header")
    @ApiModelProperty(value="响应头")
    private String responseHeader;

    /**
     * 接口状态（0-关闭，1-开启）
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="接口状态（0-关闭，1-开启）")
    private Integer status;

    /**
     * 请求类型
     */
    @TableField(value = "`method`")
    @ApiModelProperty(value="请求类型")
    private String method;

    /**
     * 创建人
     */
    @TableField(value = "create_by")
    @ApiModelProperty(value="创建人")
    private Long createBy;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 更新人
     */
    @TableField(value = "update_by")
    @ApiModelProperty(value="更新人")
    private Long updateBy;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value="是否删除(0-未删, 1-已删)")
    private Byte isDelete;
}