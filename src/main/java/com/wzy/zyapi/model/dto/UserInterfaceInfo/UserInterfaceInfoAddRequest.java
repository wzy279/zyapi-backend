package com.wzy.zyapi.model.dto.UserInterfaceInfo;

import lombok.Data;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-10-12 08:55
 **/
@Data
public class UserInterfaceInfoAddRequest {


    /**
     * 调用用户 id
     */
    private Long userId;

    /**
     * 接口 id
     */
    private Long interfaceinfoId;

    /**
     * 总调用次数
     */
    private Integer totalNum;

    /**
     * 剩余调用次数
     */
    private Integer leftNum;

    /**
     * 0-正常，1-禁用
     */
    private Integer status;


}
