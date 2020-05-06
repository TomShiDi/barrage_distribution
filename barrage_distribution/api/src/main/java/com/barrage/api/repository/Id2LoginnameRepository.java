package com.barrage.api.repository;

import com.barrage.api.entity.UseridToLoginname;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Id2LoginnameRepository extends JpaRepository<UseridToLoginname, Integer> {
    UseridToLoginname findByUserId(Integer userId);

    UseridToLoginname findByLoginName(String loginName);
}
