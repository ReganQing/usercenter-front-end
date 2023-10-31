package com.ron.usercenterbackend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ron.usercenterbackend.common.ErrorCode;
import com.ron.usercenterbackend.exception.BusinessException;
import com.ron.usercenterbackend.model.domain.User;
import com.ron.usercenterbackend.service.UserService;
import com.ron.usercenterbackend.mapper.UserMapper;
import com.ron.usercenterbackend.utils.SHA1;
import com.ron.usercenterbackend.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ron.usercenterbackend.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Ron_567
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2023-10-25 16:44:05
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;

    /**
     * 盐值 混淆密码
     */
    public static final String SALT = "Best";

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 验证密码
     * @return 返回注册用户的UID
     */
    @Override
    public String userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4 || userAccount.length() > 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号长度有问题，应在4~8位之间");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码长度过短，注意不能小于8位");
        }

        // 账户不能包含特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号含有特殊字符");
        }

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不一致");
        }
        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户重复");
        }

        // 2.加密
        // 对密码进行SHA1加密

        String encodedPassword = SHA1.encode(userPassword, SALT);

        // 3.插入数据
        User user = new User();
        SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
        String userCode= String.valueOf(idWorker.nextId());
        user.setUserCode(userCode);
        user.setUserAccount(userAccount);
        user.setUserPassword(encodedPassword);
        boolean saved = this.save(user);
        if (!saved) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据错误");
        }

        return user.getUserCode();
    }

    /**
     * 用户登录
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request 请求
     * @return 返回登录用户的脱敏信息
     */
    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验用户账户是否正确
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号或密码错误");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码不能小于8位");
        }

        // 账户不能包含特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号含有特殊字符");
        }

        // 2.校验密码是否输入正确，加密之后跟密文再进行对比
        // 对密码进行SHA1加密
        String encodedPassword = SHA1.encode(userPassword, SALT);
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encodedPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号与密码不匹配");
        }
        // todo 记录登录次数，如果登录次数异常就对该账号做限流

        // 3.用户脱敏
        User safeUser = getSafeUser(user);
        // 4.记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safeUser);

        return safeUser;
    }

    /**
     * 用户脱敏
     * @param originUser 原始用户信息
     * @return 脱敏后的用户信息
     */
    @Override
    public User getSafeUser(User originUser) {
        if (originUser == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "原账户信息异常");
        }
        User safeUser = new User();
        safeUser.setId(originUser.getId());
        safeUser.setUsername(originUser.getUsername());
        safeUser.setUserAccount(originUser.getUserAccount());
        safeUser.setAvatar(originUser.getAvatar());
        safeUser.setGender(originUser.getGender());
        safeUser.setPhoneNumber(originUser.getPhoneNumber());
        safeUser.setEmail(originUser.getEmail());
        safeUser.setUserStatus(originUser.getUserStatus());
        safeUser.setUserRole(originUser.getUserRole());
        safeUser.setUserCode(originUser.getUserCode());
        safeUser.setCreateTime(originUser.getCreateTime());

        return safeUser;
    }

    /**
     *
     * @param request 注销请求
     * @return 1 成功
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




