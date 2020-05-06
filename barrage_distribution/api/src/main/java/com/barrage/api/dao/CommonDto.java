package com.barrage.api.dao;

import java.io.Serializable;

/**
 * @Author TomShiDi
 * @Description 响应数据体基类
 * @Date 2020/3/25
 **/
public class CommonDto implements Serializable {
    private Integer code;

    private String message;

    private Object Data;

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

    public Object getData() {
        return Data;
    }

    public void setData(Object data) {
        Data = data;
    }

    @Override
    public String toString() {
        return "CommonDto{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", Data=" + Data +
                '}';
    }
}
