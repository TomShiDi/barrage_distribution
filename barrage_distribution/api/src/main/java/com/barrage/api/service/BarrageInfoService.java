package com.barrage.api.service;


import com.barrage.api.entity.BarrageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface BarrageInfoService {

    BarrageInfo findByBarrageId(Integer id);

    List<BarrageInfo> findBySenderId(Integer senderId);

    Page<BarrageInfo> getBarragePageByIndex(Pageable pageable);

    BarrageInfo saveBarrageInfo(BarrageInfo barrageInfo);

    void deleteBarrageInfo(Integer barrageId);
}
