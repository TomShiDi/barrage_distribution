package com.barrage.api.enums;

/**
 * @Author TomShiDi
 * @Date 2020年3月25日13:55:26
 * @Description
 */
public enum BarrageExceptionEnum {


    /**
     * 项目异常枚举
     */
    TOP_EXCEPTION(0, "顶级异常"),
    USER_INFO_SAVE_ERROR(1, "用户信息保存错误"),
    BARRAGE_INFO_QUERY_ERROR(2, "弹幕id不存在"),
    BARRAGE_INFO_SAVE_ERROR(3, "弹幕信息保存错误"),
    USER_ACCOUNT_INFO_SAVE_ERROR(4, "用户账号信息保存出错"),
    REFLECTION_NOT_EXIST(5, "映射关系不存在"),
    REFLECTION_SAVE_ERROR(6, "映射关系保存失败"),
    BARRAGE_SUBMIT_FORM_ERROR(20, "弹幕提交表单错误"),
    GET_BARRAGE_ERROR(21, "获取弹幕数据失败"),
    USER_INFO_NOT_FOUND(8, "没有找到用户信息"),
    LOGIN_INFO_INVALID(23, "信息不全"),
    LOGIN_SUCCESS(24, "登录成功"),
    LOGIN_FAILED(25, "账号或密码错误"),
    REGISTER_SUCCESS(26, "注册成功"),
    REGISTER_FAILED(27, "注册失败"),;


    private Integer code;

    private String message;

    BarrageExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

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
}
