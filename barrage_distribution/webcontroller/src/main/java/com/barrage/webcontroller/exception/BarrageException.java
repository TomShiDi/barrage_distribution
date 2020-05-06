package com.barrage.webcontroller.exception;


import com.barrage.api.enums.BarrageExceptionEnum;

public class BarrageException extends RuntimeException {

    private Integer code;

//    private String message;

    public BarrageException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public BarrageException(BarrageExceptionEnum barrageExceptionEnum) {

        super(barrageExceptionEnum.getMessage());
        this.code = barrageExceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
