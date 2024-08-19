package org.example.common.entity;

public enum ResultCode {
    // 基本
    SUCCESS(200, "成功"),
    UNKNOWN(501, "服务器异常"),
    // jwt 异常
    LoginError(40001, "登录校验失败，请您重新登录"),
    ;
    /**
     * 代码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
