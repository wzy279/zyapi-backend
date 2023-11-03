package com.wzy.zyapi.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
* @description
*
* @author: Wangzhaoyi
* @create: 2023-10-11 22:05
**/

/**
    * 用户调用接口关系
    */
@ApiModel(description="用户调用接口关系")
@Schema(description="用户调用接口关系")
@Data
@TableName(value = "user_interface_info")
public class UserInterfaceInfo {
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
     * 接口 id
     */
    @TableField(value = "interfaceInfo_id")
    @ApiModelProperty(value="接口 id")
    @Schema(description="接口 id")
    private Long interfaceinfoId;

    /**
     * 总调用次数
     */
    @TableField(value = "total_num")
    @ApiModelProperty(value="总调用次数")
    @Schema(description="总调用次数")
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    @TableField(value = "left_num")
    @ApiModelProperty(value="剩余调用次数")
    @Schema(description="剩余调用次数")
    private Integer leftNum;

    /**
     * 0-正常，1-禁用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="0-正常，1-禁用")
    @Schema(description="0-正常，1-禁用")
    private Integer status;

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
}
