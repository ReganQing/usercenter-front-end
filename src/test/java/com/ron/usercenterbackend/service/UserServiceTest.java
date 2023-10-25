package com.ron.usercenterbackend.service;

import com.ron.usercenterbackend.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * 用户服务测试
 * @author Ron_567
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testAddUser() {
        User user = new User();
        user.setId(0L);
        user.setUsername("Meiko");
        user.setUserAccount("1234");
        user.setAvatar("\thttps://profile-avatar.csdnimg.cn/5817f904e87c430b9b5343a4743db6ee_qq_41488033.jpg!1");
        user.setGender(0);
        user.setUserPassword("111111");
        user.setPhoneNumber("1111333333");
        user.setEmail("222222@ff.com");
        user.setUserStatus(0);
        user.setUserRole(0);
        user.setUserCode("0001");

        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "steve";
        String userPassword = "";
        String checkPassword = "12345678";
        long res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, res);

        userAccount = "ron";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, res);

        userAccount = "haha";
        userPassword = "1234";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, res);

        userAccount = "1234";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, res);

        userAccount = "ho r se";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, res);

        userAccount = "1234556";
        userPassword = "12345678";
        checkPassword = "123456789";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, res);

        userAccount = "19927834";
        userPassword = "111122223333";
        checkPassword = "111122223333";
        res = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, res);





    }
}