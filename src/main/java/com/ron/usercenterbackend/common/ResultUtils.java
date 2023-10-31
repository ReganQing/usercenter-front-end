package com.ron.usercenterbackend.common;

/**
 * 返回工具类
 * @author Ron_567
 */
public class ResultUtils {
    /**
     * 成功
     * @param data 返回数据
     * @return 成功提示
     * @param <T> 泛型
     */
    public static <T> BasicResponse<T> success(T data) {
        return new BasicResponse<>(0, data, "OK");
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @return 失败提示
     * @param <T> 泛型
     */
    public static <T> BasicResponse<T> error(ErrorCode errorCode) {
        return new BasicResponse<>(errorCode);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @param message 错误信息
     * @param description 错误描述
     * @return 失败提示
     */
    public static BasicResponse error(ErrorCode errorCode, String message, String description) {
        return new BasicResponse(errorCode.getCode(), null, message, description);
    }

    /**
     * 失败
     * @param errorCode 错误码
     * @param description 错误描述
     * @return 失败提示
     */
    public static BasicResponse error(ErrorCode errorCode,  String description) {
        return new BasicResponse(errorCode.getCode(), null,  errorCode.getMessage(), description);
    }

    /**
     * 失败
     * @param code 错误码
     * @param message 错误信息
     * @param description 错误描述
     * @return 失败提示
     */
    public static BasicResponse error(int code, String message, String description) {
        return new BasicResponse(code, null, message, description);
    }
}
