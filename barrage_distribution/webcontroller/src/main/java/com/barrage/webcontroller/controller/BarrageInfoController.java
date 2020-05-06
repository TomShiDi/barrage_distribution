package com.barrage.webcontroller.controller;


import com.barrage.webcontroller.exception.BarrageException;
import com.barrage.api.dao.BarrageDto;
import com.barrage.api.entity.BarrageInfo;
import com.barrage.api.enums.BarrageExceptionEnum;
import com.barrage.api.service.BarrageInfoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

//@Api(value = "弹幕信息操作接口")
@RestController
@RequestMapping("/barrage")
public class BarrageInfoController {

    private BarrageInfoService barrageInfoService;


    @Autowired
    public BarrageInfoController(BarrageInfoService barrageInfoService) {
        this.barrageInfoService = barrageInfoService;
    }

    /**
     * 弹幕信息保存接口
     *
     * @param barrageInfo   提交的表单实体
     * @param bindingResult 表单验证结果
     * @param map           拓展map
     * @return BarrageDto 展示数据类
     */
    @ApiOperation(value = "保存用户发送的弹幕", notes = "", response = BarrageDto.class)
    @ApiImplicitParam(name = "barrageInfo", value = "弹幕信息form数据", dataTypeClass = BarrageInfo.class, required = true)
    @PostMapping("/save")
    public BarrageDto save(@Valid BarrageInfo barrageInfo,
                           BindingResult bindingResult,
                           Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            BarrageDto barrageDto = new BarrageDto();
            barrageDto.setMessage(bindingResult.getFieldError().getDefaultMessage());
            return barrageDto;
        }

        barrageInfoService.saveBarrageInfo(barrageInfo);

        BarrageDto barrageDto = new BarrageDto();
        barrageDto.setMessage("success");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("barrageInfo", barrageInfo);
        barrageDto.setResultData(resultMap);

        return barrageDto;
    }

    /**
     * 获取指定页的弹幕信息
     *
     * @param index 页码
     * @param size  每一页的数量
     * @return
     */
    @ApiOperation(value = "获取指定页码数的弹幕数据", response = BarrageDto.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "index", value = "待取弹幕数据的页码", defaultValue = "1", dataTypeClass = Integer.class, paramType = "query"),
            @ApiImplicitParam(name = "size", value = "每一页的数据条数", defaultValue = "10", dataTypeClass = Integer.class, paramType = "query")
    })
    @GetMapping("/getData")
    public BarrageDto getIndexPage(@RequestParam(name = "index", defaultValue = "1") Integer index,
                                   @RequestParam(name = "size", defaultValue = "20") Integer size) {
        BarrageDto barrageDto = new BarrageDto();
        PageRequest request = PageRequest.of(index - 1, size);
        Page<BarrageInfo> barrageInfoPage = barrageInfoService.getBarragePageByIndex(request);
        if (barrageInfoPage == null || barrageInfoPage.getTotalElements() == 0) {
//            barrageDto.setMessage("获取弹幕失败");
//            return barrageDto;
            throw new BarrageException(BarrageExceptionEnum.GET_BARRAGE_ERROR);
        } else {
            barrageDto.setMessage("success " + "index: " + index);
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("barrageInfoPage", barrageInfoPage);
            barrageDto.setResultData(resultMap);
            return barrageDto;
        }
    }

    /**
     * 弹幕点赞接口
     *
     * @param barrageId 待点赞弹幕的id
     * @return
     */
    @ApiOperation(value = "给指定弹幕点赞", response = BarrageDto.class)
    @ApiImplicitParam(name = "barrageId", value = "弹幕id", required = true, dataTypeClass = Integer.class)
    @GetMapping("/star")
    public BarrageDto starBarrage(@RequestParam(name = "barrageId") Integer barrageId) {
        BarrageInfo barrageInfo = barrageInfoService.findByBarrageId(barrageId);
        barrageInfo.setStarNum(barrageInfo.getStarNum() + 1);
        barrageInfoService.saveBarrageInfo(barrageInfo);

        BarrageDto barrageDto = new BarrageDto();
        barrageDto.setMessage("success");
        return barrageDto;
    }

    @ApiOperation(httpMethod = "get", value = "取消点赞", response = BarrageDto.class)
    @ApiImplicitParam(name = "barrageId", value = "弹幕Id", required = true, dataTypeClass = Integer.class)
    @GetMapping("/unstar")
    public BarrageDto unStarBarrage(@RequestParam(name = "barrageId") Integer barrageId) {
        BarrageInfo barrageInfo = barrageInfoService.findByBarrageId(barrageId);
        barrageInfo.setStarNum(barrageInfo.getStarNum() - 1);
        barrageInfoService.saveBarrageInfo(barrageInfo);

        BarrageDto barrageDto = new BarrageDto();
        barrageDto.setMessage("unstar success");
        return barrageDto;
    }

}
