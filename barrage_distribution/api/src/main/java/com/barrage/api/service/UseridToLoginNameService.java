package com.barrage.api.service;


import com.barrage.api.entity.UseridToLoginname;

public interface UseridToLoginNameService {
    UseridToLoginname findByUserId(Integer userId);

    UseridToLoginname findByLoginname(String loginName);

    UseridToLoginname save(UseridToLoginname useridToLoginname);
}
