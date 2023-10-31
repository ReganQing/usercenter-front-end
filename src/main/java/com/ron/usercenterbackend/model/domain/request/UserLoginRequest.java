package com.ron.usercenterbackend.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 * @author Ron_567
 */

@Data
public class UserLoginRequest implements Serializable {


    private static final long serialVersionUID = 8868963247751835329L;

    private String userAccount;

    private String userPassword;
}
