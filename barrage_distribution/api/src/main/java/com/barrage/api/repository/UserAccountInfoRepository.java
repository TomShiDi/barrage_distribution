package com.barrage.api.repository;

import com.barrage.api.entity.UserAccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountInfoRepository extends JpaRepository<UserAccountInfo, String> {
    UserAccountInfo findByUserLoginName(String userLoginName);
}
