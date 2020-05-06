package com.barrage.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UseridToLoginname {

    @Id
    private Integer userId;

    private String loginName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    @Override
    public String toString() {
        return "UseridToLoginname{" +
                "userId=" + userId +
                ", loginName='" + loginName + '\'' +
                '}';
    }
}
