package com.barrage.api.service;


import com.barrage.api.entity.UserInfo;

import java.util.List;

/**
 * @Author TomShiDi
 * @Date 2020年3月25日13:47:46
 * @Description
 */
public interface UserInfoService {
    /**
     * 根据用户编号查找
     *
     * @param userId 用户编号
     * @return 一条用户信息
     */
    UserInfo findByUserId(Integer userId);

    List<UserInfo> findByNickName(String nickName);

    UserInfo findByUserPhoneNum(String phoneNum);

    UserInfo findByUserEmailAndStatus(String userEmail, Integer status);

    UserInfo findByUserEmail(String userEmail);

    void deleteByUserId(Integer userId);

    public void deleteByUserId(Integer userId, String userEmail);

    UserInfo saveUserInfo(UserInfo userInfo);

    UserInfo updateUserInfo(UserInfo userInfo);

    int updateUserAccountStatus(String userEmail, Integer status);
}
