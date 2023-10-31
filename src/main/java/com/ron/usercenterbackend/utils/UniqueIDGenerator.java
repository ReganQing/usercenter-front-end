package com.ron.usercenterbackend.utils;

import java.util.UUID;

/**
 * @author Ron_567
 */
public class UniqueIDGenerator {
    public static String generateUniqueID() {
        UUID uuid = UUID.randomUUID();
        long mostSignificantBits = uuid.getMostSignificantBits();
        long leastSignificantBits = uuid.getLeastSignificantBits();

        // 使用 XOR 运算将两个 long 值合并为一个 long 值
        long combinedBits = mostSignificantBits ^ leastSignificantBits;

        // 将合并后的 long 值转换为6位的字符串
        String uid = Long.toString(combinedBits, 36);

        // 确保字符串长度为6位，不足6位的在前面补零
        while (uid.length() < 6) {
            uid = "0" + uid;
        }

        return uid;
    }
}

