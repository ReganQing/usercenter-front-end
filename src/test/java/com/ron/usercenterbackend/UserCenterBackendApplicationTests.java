package com.ron.usercenterbackend;

import com.ron.usercenterbackend.utils.SHA1;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sun.security.provider.SHA;

@SpringBootTest
class UserCenterBackendApplicationTests {

    @Test
    void testEncode() {
        String newPassword = SHA1.encode("hhhh1111", "6788");
        System.out.println(newPassword);
    }

    @Test
    void contextLoads() {
    }

}
