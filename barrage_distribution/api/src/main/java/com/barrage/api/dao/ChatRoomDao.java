package com.barrage.api.dao;

public class ChatRoomDao {
    private Integer code;

    private String message;

    private String confirmNickName;

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

    public String getConfirmNickName() {
        return confirmNickName;
    }

    public void setConfirmNickName(String confirmNickName) {
        this.confirmNickName = confirmNickName;
    }

    public ChatRoomDao code(Integer code) {
        this.code = code;
        return this;
    }

    public ChatRoomDao message(String message) {
        this.message = message;
        return this;
    }

    public ChatRoomDao confirmNickName(String confirmNickName) {
        this.confirmNickName = confirmNickName;
        return this;
    }
}
