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


}
