package com.wzy.zyapi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-11-04 15:41
**/

/**
    * 星火大模型访问记录
    */
@ApiModel(description="星火大模型访问记录")
@Schema(description="星火大模型访问记录")
@Data
@TableName(value = "model_info")
public class ModelInfo implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    @Schema(description="主键")
    private Long id;

    /**
     * 调用用户 id
     */
    @TableField(value = "user_id")
    @ApiModelProperty(value="调用用户 id")
    @Schema(description="调用用户 id")
    private Long userId;

    /**
     * 聊天记录名称
     */
    @TableField(value = "`name`")
    @ApiModelProperty(value="聊天记录名称")
    @Schema(description="聊天记录名称")
    private String name;

    /**
     * 聊天记录
     */
    @TableField(value = "message")
    @ApiModelProperty(value="聊天记录")
    @Schema(description="聊天记录")
    private String message;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    @Schema(description="创建时间")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    @Schema(description="更新时间")
    private Date updateTime;

    /**
     * 是否删除(0-未删, 1-已删)
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value="是否删除(0-未删, 1-已删)")
    @Schema(description="是否删除(0-未删, 1-已删)")
    private Byte isDelete;

    private static final long serialVersionUID = 1L;
}
