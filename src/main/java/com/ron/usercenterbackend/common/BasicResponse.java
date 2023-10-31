package com.ron.usercenterbackend.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 * @param <T>
 * @author Ron_567
 */
@Data
public class BasicResponse<T> implements Serializable {
    private static final long serialVersionUID = -2585828136068287570L;

    private int code;

    private T data;

    private String message;

    private String description;

    // 构造函数
    public BasicResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BasicResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BasicResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BasicResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }

}
