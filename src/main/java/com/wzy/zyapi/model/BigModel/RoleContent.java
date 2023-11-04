package com.wzy.zyapi.model.BigModel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description
 * @author: Wangzhaoyi
 * @create: 2023-11-04 10:50
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleContent {

    public static final String ROLE_USER = "user";

    public static final String ROLE_ASSISTANT = "assistant";

    private String role;

    private String content;

    public static RoleContent createUserRoleContent(String content) {
        return new RoleContent(ROLE_USER, content);
    }

    public static RoleContent createAssistantRoleContent(String content) {
        return new RoleContent(ROLE_ASSISTANT, content);
    }

}
