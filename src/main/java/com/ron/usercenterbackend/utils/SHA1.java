package com.ron.usercenterbackend.utils;

import org.apache.tomcat.util.buf.HexUtils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author Ron_567
 */
public class SHA1 {
    public static String encode(String str, String salt) {
        String saltStr = str + salt;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(saltStr.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md.digest();
            return byteToHexString(digest);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String byteToHexString(byte[] bytes) {
        return String.valueOf(HexUtils.toHexString(bytes));
    }
    public static void main(String[] args) {
        SHA1 sha1 = new SHA1();
        String content = "测试test";
        String salt = "666";
        System.out.println(sha1.encode(content, salt));
    }
}
