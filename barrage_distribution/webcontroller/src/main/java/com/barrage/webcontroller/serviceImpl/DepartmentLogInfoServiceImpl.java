package com.barrage.webcontroller.serviceImpl;//package barrage.demo.serviceImpl;
//
//import barrage.demo.barrageapi.entity.DepartmentLogInfo;
//import barrage.demo.mapper.DepartmentLogInfoMapper;
//import barrage.demo.service.DepartmentLogInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import tk.mybatis.mapper.barrageapi.entity.Example;
//
//import java.util.List;
//
///**
// * @Author TomShiDi
// * @Since 2019/6/17
// * @Version 1.0
// */
//@Service
//@Transactional
//public class DepartmentLogInfoServiceImpl implements DepartmentLogInfoService {
//
//    private DepartmentLogInfoMapper departmentLogInfoMapper;
//
//
//    @Autowired
//    public DepartmentLogInfoServiceImpl(DepartmentLogInfoMapper departmentLogInfoMapper) {
//        this.departmentLogInfoMapper = departmentLogInfoMapper;
//    }
//
//    @Override
//    public Page<DepartmentLogInfo> findAll(Pageable pageable) {
//        return new PageImpl<>(departmentLogInfoMapper.selectAllPageable(pageable.getPageNumber(), pageable.getPageSize()));
//    }
//
//    @Override
//    public Page<DepartmentLogInfo> findByDepartmentId(Integer departmentId,Pageable pageable) {
//        return new PageImpl<>(departmentLogInfoMapper.findByDepartmentId(departmentId, pageable.getPageNumber(), pageable.getPageSize()));
//    }
//
//    @Override
//    public Page<DepartmentLogInfo> findByOperatorId(Integer operatorId, Pageable pageable) {
//        return new PageImpl<>(departmentLogInfoMapper.findByOperatorId(operatorId, pageable.getPageNumber(), pageable.getPageSize()));
//    }
//
//    @Override
//    public int save(DepartmentLogInfo departmentLogInfo) {
//        return departmentLogInfoMapper.insert(departmentLogInfo);
//    }
//
//}
