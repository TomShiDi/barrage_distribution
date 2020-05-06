package com.barrage.api.enums;

public enum ChatRoomEnum {
    /**
     * 聊天室静态变量枚举
     */
    SUCCESS(100),;

    private Integer code;

    ChatRoomEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
