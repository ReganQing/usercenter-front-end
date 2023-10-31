package com.ron.usercenterbackend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 * @author Ron_567
 */

@Data
public class UserRegisterRequest implements Serializable {
    // 序列化，防止发生冲突
    private static final long serialVersionUID = -7586174769130227267L;

    private String userAccount;

    private String userPassword;

    private String checkPassword;

}
