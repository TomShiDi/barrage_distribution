package com.barrage.webcontroller.mapper;//package barrage.demo.mapper;
//
//import barrage.demo.barrageapi.entity.DepartmentLogInfo;
//import org.apache.ibatis.annotations.Param;
//import org.springframework.stereotype.Repository;
//import tk.mybatis.mapper.common.Mapper;
//
//import java.util.List;
//
//@Repository
//public interface DepartmentLogInfoMapper extends Mapper<DepartmentLogInfo> {
//
//    List<DepartmentLogInfo> selectAllPageable(@Param("index") int index, @Param("pageSize") int pageSize);
//
//    List<DepartmentLogInfo> findByDepartmentId(@Param("departmentId") int departmentId,
//                                               @Param("index") int index,
//                                               @Param("pageSize") int pageSize);
//
//    List<DepartmentLogInfo> findByOperatorId(@Param("operatorId") int departmentId,
//                                               @Param("index") int index,
//                                               @Param("pageSize") int pageSize);
//}