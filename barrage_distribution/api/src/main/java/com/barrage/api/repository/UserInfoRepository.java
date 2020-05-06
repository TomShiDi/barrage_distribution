package com.barrage.api.repository;

import com.barrage.api.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    UserInfo findByUserId(Integer userId);

    List<UserInfo> findByNickName(String nickName);

    UserInfo findByUserPhoneNum(String userPhoneNum);

    UserInfo findByUserEmailAndUserStatus(String userEmail, Integer status);

    UserInfo findByUserEmail(String userEmail);

    void deleteByUserId(Integer userId);

    @Transactional(rollbackFor = RuntimeException.class)
    @Modifying(clearAutomatically = false)
    @Query(value = "update user_info set user_info.user_status = ?2 where user_info.user_email = ?1", nativeQuery = true)
    int updateUserAccountStatus(String userEmail, Integer status);

}
