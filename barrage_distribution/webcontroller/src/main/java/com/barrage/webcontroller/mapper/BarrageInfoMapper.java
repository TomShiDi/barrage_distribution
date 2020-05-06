package com.barrage.webcontroller.mapper;

import com.barrage.api.entity.BarrageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author TomShiDi
 * @Since 2019/6/10
 * @Version 1.0
 */
@Mapper
@Component
public interface BarrageInfoMapper {

    BarrageInfo findByBarrageId(@Param("id") Integer id);

    List<BarrageInfo> findBySenderId(@Param("senderId") Integer senderId);

    List<BarrageInfo> getBarragePageByIndex(@Param("index") int index, @Param("pageSize") int pageSize);

    int saveBarrageInfo(BarrageInfo barrageInfo);

    int update(BarrageInfo barrageInfo);
}
