package com.barrage.api.enums;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/4/15
 **/
public enum AuthEnums {
    /**
     * 用户账号状态
     */
    STATUS_NO_ACTIVATION(0, "未激活状态"),
    STATUS_ACTIVATION(1, "已激活状态"),
    STATUS_BANNED(2, "被禁用状态"),
    ACTIVATE_SUCCESS(3, "激活成功"),
    ACTIVATE_FAILED(4, "激活失败"),
    TRUE(5, "true"),
    FALSE(6, "false"),
    INCORRECT_ACCOUNT_INFO(7, "账号或密码错误"),
    ;

    private Integer code;

    private String message;

    AuthEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
