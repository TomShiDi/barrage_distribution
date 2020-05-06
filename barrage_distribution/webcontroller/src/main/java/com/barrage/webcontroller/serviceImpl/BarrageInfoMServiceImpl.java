//package com.barrage.webcontroller.serviceImpl;
//
//import com.barrage.webcontroller.exception.BarrageException;
//import com.barrage.webcontroller.mapper.BarrageInfoMapper;
//import com.barrage.api.entity.BarrageInfo;
//import com.barrage.api.service.BarrageInfoMService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
///**
// * @Author TomShiDi
// * @Since 2019/6/10
// * @Version 1.0
// */
//@Service
//@Transactional(rollbackFor = BarrageException.class)
//public class BarrageInfoMServiceImpl implements BarrageInfoMService {
//
//    private BarrageInfoMapper barrageInfoMapper;
//
//    @Autowired
//    public BarrageInfoMServiceImpl(BarrageInfoMapper barrageInfoMapper) {
//        this.barrageInfoMapper = barrageInfoMapper;
//    }
//
//    @Override
//    public BarrageInfo findByBarrageId(Integer id) {
//        return barrageInfoMapper.findByBarrageId(id);
//    }
//
//    @Override
//    public List<BarrageInfo> findBySenderId(Integer senderId) {
//        return barrageInfoMapper.findBySenderId(senderId);
//    }
//
//    @Override
//    public List<BarrageInfo> getBarragePageByIndex(Pageable pageable) {
//        return barrageInfoMapper.getBarragePageByIndex(pageable.getPageNumber() + 1, pageable.getPageSize());
//    }
//
//    @Override
//    public BarrageInfo saveBarrageInfo(BarrageInfo barrageInfo) {
//        int modifyRow = barrageInfoMapper.saveBarrageInfo(barrageInfo);
//        return (modifyRow == 1 ? barrageInfoMapper.findByBarrageId(barrageInfo.getBarrageId()) : null);
//    }
//
//    @Override
//    public int update(BarrageInfo barrageInfo) {
//        return barrageInfoMapper.update(barrageInfo);
//    }
//}
