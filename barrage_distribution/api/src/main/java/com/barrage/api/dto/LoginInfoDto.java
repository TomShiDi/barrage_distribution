package com.barrage.api.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/4/9
 **/
public class LoginInfoDto implements Serializable {
    @NotEmpty(message = "用户名不能为空")
    private String userName;

    @NotEmpty(message = "密码不能为空")
    private String password;

//    @NotEmpty(message = "验证码不能为空")
    private String authCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
}
