//package com.barrage.webcontroller.serviceImpl;
//
//import barrage.demo.entity.UseridToLoginname;
//import barrage.demo.enums.BarrageExceptionEnum;
//import barrage.demo.exception.BarrageException;
//import barrage.demo.repository.Id2LoginnameRepository;
//import barrage.demo.service.UseridToLoginNameService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Transactional
//public class Id2LoginNameServiceImpl implements UseridToLoginNameService {
//
//    private Id2LoginnameRepository repository;
//
//    @Autowired
//    public Id2LoginNameServiceImpl(Id2LoginnameRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public UseridToLoginname findByUserId(Integer userId) {
//        UseridToLoginname useridToLoginname = repository.findByUserId(userId);
//
//        if (useridToLoginname == null) {
//            throw new BarrageException(BarrageExceptionEnum.REFLECTION_NOT_EXIST);
//        }
//
//        return useridToLoginname;
//    }
//
//    @Override
//    public UseridToLoginname findByLoginname(String loginName) {
//        UseridToLoginname useridToLoginname = repository.findByLoginName(loginName);
//
//        if (useridToLoginname == null) {
//            throw new BarrageException(BarrageExceptionEnum.REFLECTION_NOT_EXIST);
//        }
//
//        return useridToLoginname;
//    }
//
//    @Override
//    public UseridToLoginname save(UseridToLoginname useridToLoginname) {
//        UseridToLoginname result = repository.save(useridToLoginname);
//        if (result == null) {
//            throw new BarrageException(BarrageExceptionEnum.REFLECTION_SAVE_ERROR);
//        }
//
//        return useridToLoginname;
//    }
//}
