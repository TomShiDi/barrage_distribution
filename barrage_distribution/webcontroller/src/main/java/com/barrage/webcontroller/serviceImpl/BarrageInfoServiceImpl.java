package com.barrage.webcontroller.serviceImpl;


import com.barrage.webcontroller.exception.BarrageException;
import com.barrage.api.entity.BarrageInfo;
import com.barrage.api.enums.BarrageExceptionEnum;
import com.barrage.api.repository.BarrageInfoRepository;
import com.barrage.api.service.BarrageInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author Tomshidi
 * @Date 2020年4月25日14:32:32
 * @Description 弹幕服务实现类
 */
@Service
@Transactional
//@MethodLog
public class BarrageInfoServiceImpl implements BarrageInfoService {

    private BarrageInfoRepository repository;

    public BarrageInfoServiceImpl() {
    }

    @Autowired
    public BarrageInfoServiceImpl(BarrageInfoRepository repository) {
        this.repository = repository;
    }


    @Cacheable(cacheNames = "myCache",keyGenerator = "keyGenerator")
    @Override
    public BarrageInfo findByBarrageId(Integer id) {
        BarrageInfo barrageInfo = repository.findByBarrageId(id);
        if (barrageInfo == null) {
            throw new BarrageException(BarrageExceptionEnum.BARRAGE_INFO_QUERY_ERROR);
        }

        return barrageInfo;
    }

    @Cacheable(cacheNames = "myCache",keyGenerator = "keyGenerator")
    @Override
    public List<BarrageInfo> findBySenderId(Integer senderId) {

        return repository.findByBarrageSenderId(senderId);
    }

    @Cacheable(cacheNames = "myCache",
            key = "#root.targetClass+'['+#root.methodName+']'+'['+#pageable.pageNumber+','+#pageable.pageSize+']'")
    @Override
    public Page<BarrageInfo> getBarragePageByIndex(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public BarrageInfo saveBarrageInfo(BarrageInfo barrageInfo) {
        BarrageInfo result = repository.save(barrageInfo);

        if (result == null) {
            throw new BarrageException(BarrageExceptionEnum.BARRAGE_INFO_SAVE_ERROR);
        }

        return barrageInfo;
    }

    @Override
    public void deleteBarrageInfo(Integer barrageId) {
        repository.deleteByBarrageId(barrageId);

    }
}
