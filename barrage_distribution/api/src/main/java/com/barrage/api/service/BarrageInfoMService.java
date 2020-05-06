package com.barrage.api.service;

import com.barrage.api.entity.BarrageInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author TomShiDi
 * @Since 2019/6/10
 * @Version 1.0
 */
public interface BarrageInfoMService {
    BarrageInfo findByBarrageId(Integer id);

    List<BarrageInfo> findBySenderId(Integer senderId);

    List<BarrageInfo> getBarragePageByIndex(Pageable pageable);

    BarrageInfo saveBarrageInfo(BarrageInfo barrageInfo);

    int update(BarrageInfo barrageInfo);
}
