package com.ron.usercenterbackend.service;

import com.ron.usercenterbackend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Ron_567
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-10-25 16:44:05
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param verifyCode 验证码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String verifyCode);

    /**
     *
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @return 脱敏后的当前登录用户信息
     */
    User doLogin(String userAccount, String userPassword);
}
