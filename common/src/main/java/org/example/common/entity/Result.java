package org.example.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 田云
 * @Description
 * @date 2020/5/1 20:54
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {
    private boolean success;
    private Integer code;
    private String message;
    private T data;

    public static <T> Result<T> result(Boolean flag, ResultCode resultCode, T data) {
        return result(flag, resultCode.getCode(), resultCode.getMessage(), data);
    }

    public static <T> Result<T> result(Boolean flag, Integer code, String message, T data) {
        return new Result<T>(flag, code, message, data);
    }


    public static <T> Result<T> success(T data) {
        return result(true, ResultCode.SUCCESS, data);
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> failure(Integer code, String message) {
        return new Result<T>(false, code, message, null);
    }

    public static <T> Result<T> failure(String message) {
        return result(false, ResultCode.UNKNOWN.getCode(), message, null);
    }
    public static <T> Result<T> failure(ResultCode resultCode) {
        return result(false, resultCode.getCode(), resultCode.getMessage(), null);
    }
}
