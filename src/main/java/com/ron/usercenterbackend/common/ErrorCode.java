package com.ron.usercenterbackend.common;

import lombok.Getter;

/**
 * 错误码
 * @author Ron_567
 */
@Getter
public enum ErrorCode {
    SUCCESS(0, "OK", "成功"),
    PARAMS_ERROR(4000, "请求参数错误", "请检查输入参数"),
    NULL_ERROR(4001, "请求数据为空", ""),
    NOT_LOGIN(4010, "未登录", "请登录后查看"),
    NO_AUTH(4011, "无权限", ""),
    SYSTEM_ERROR(5000, "系统内部异常", "");

    /**
     * 状态码
     */
    public final int code;
    /**
     * 状态码信息
     */
    public final String message;

    /**
     * 状态码描述
     */
    public final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

}
