package com.barrage.api.repository;



import com.barrage.api.entity.BarrageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Tomshidi
 * @date 2020年5月2日13:09:15
 * @Description
 */
@Repository
public interface BarrageInfoRepository extends JpaRepository<BarrageInfo, Integer> {

    BarrageInfo findByBarrageId(Integer barrageId);

    List<BarrageInfo> findByBarrageSenderId(Integer senderId);

    void deleteByBarrageId(Integer barrageId);
}
