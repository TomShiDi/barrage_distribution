package com.barrage.webcontroller.serviceImpl;


import com.barrage.webcontroller.exception.BarrageException;
import com.barrage.api.entity.UserInfo;
import com.barrage.api.enums.BarrageExceptionEnum;
import com.barrage.api.repository.UserInfoRepository;
import com.barrage.api.service.UserInfoService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Tomshidi
 * @Date 2020年4月16日13:06:54
 * @Description
 */
@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoRepository repository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository repository) {
        this.repository = repository;
    }

    @Cacheable(cacheNames = "myCache",keyGenerator = "keyGenerator")
    @Override
    public UserInfo findByUserId(Integer userId) {
        UserInfo userInfo = repository.findByUserId(userId);
        return userInfo;
    }

    @Cacheable(cacheNames = "myCache",keyGenerator = "keyGenerator")
    @Override
    public List<UserInfo> findByNickName(String nickName) {
        List<UserInfo> userInfoList = repository.findByNickName(nickName);
        return userInfoList;
    }

    @Cacheable(cacheNames = "myCache",keyGenerator = "keyGenerator")
    @Override
    public UserInfo findByUserPhoneNum(String phoneNum) {
        UserInfo userInfo = repository.findByUserPhoneNum(phoneNum);
        return userInfo;
    }

    @Cacheable(cacheNames = "myCache",keyGenerator = "keyGenerator")
    @Override
    public UserInfo findByUserEmail(String userEmail) {
        return repository.findByUserEmail(userEmail);
    }

    @Cacheable(cacheNames = "myCache",keyGenerator = "keyGenerator")
    @Override
    public UserInfo findByUserEmailAndStatus(String userEmail,Integer status) {
        return repository.findByUserEmailAndUserStatus(userEmail,status);
    }


    @Override
    public void deleteByUserId(Integer userId) {
        UserInfo userInfo = repository.findByUserId(userId);
        if (userInfo == null) {
            throw new BarrageException(BarrageExceptionEnum.USER_INFO_NOT_FOUND);
        }
        deleteByUserId(userId, userInfo.getUserEmail());
    }

    @CacheEvict(cacheNames = "myCache",key = "#root.targetClass +'['+#userEmail+']'")
    @Override
    public void deleteByUserId(Integer userId, String userEmail) {
        repository.deleteByUserId(userId);
    }

    @CachePut(cacheNames = "myCache", key = "#root.targetClass +'['+#userInfo.userEmail+']'")
    @Override
    public UserInfo saveUserInfo(UserInfo userInfo) {
        UserInfo result = repository.save(userInfo);
        if (result.getUserId() == null) {
            throw new BarrageException(BarrageExceptionEnum.USER_INFO_SAVE_ERROR);
        }
        return result;
    }

    @CachePut(cacheNames = "myCache", key = "#root.targetClass +'['+#userInfo.userEmail+']'")
    @Override
    public UserInfo updateUserInfo(UserInfo userInfo) {
        UserInfo result = repository.findByUserId(userInfo.getUserId());
        if (result == null) {
            throw new BarrageException(BarrageExceptionEnum.USER_INFO_NOT_FOUND);
        }
        return repository.save(userInfo);
    }

    @CachePut(cacheNames = "myCache", key = "#root.targetClass +'['+#userEmail+']'")
    @Override
    public int updateUserAccountStatus(String userEmail, Integer status) {
        return repository.updateUserAccountStatus(userEmail, status);
    }
}
