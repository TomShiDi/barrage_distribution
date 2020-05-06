package com.barrage.api.service;


import com.barrage.api.entity.UserAccountInfo;

public interface UserAccountInfoService {
    UserAccountInfo findByUserLoginName(String loginName);

    UserAccountInfo saveAccountInfo(UserAccountInfo userAccountInfo);
}
