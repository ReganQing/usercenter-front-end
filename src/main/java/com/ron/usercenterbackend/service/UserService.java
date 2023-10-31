package com.ron.usercenterbackend.service;

import com.ron.usercenterbackend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author Ron_567
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2023-10-25 16:44:05
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 验证密码
     * @return 新用户 id
     */
    String userRegister(String userAccount, String userPassword, String checkPassword);

    /**
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request 请求
     * @return 脱敏后的当前登录用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     * @param originUser 原始用户信息
     * @return 脱敏后的用户信息
     */
    User getSafeUser(User originUser);

    /**
     * 请求用户注销
     * @param request 注销请求
     *
     */
    int userLogout(HttpServletRequest request);
}
