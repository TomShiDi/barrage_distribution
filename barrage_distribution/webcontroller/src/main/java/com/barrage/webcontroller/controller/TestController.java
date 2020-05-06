package com.barrage.webcontroller.controller;


import com.barrage.api.builders.ResponseDtoBuilder;
import com.barrage.api.dao.CommonDto;
import com.barrage.api.enums.AuthEnums;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @Author TomShiDi
 * @Since 2019/5/22
 * @Version 1.0
 */
@RestController
//@RequestMapping
public class TestController {


    @ModelAttribute(value = "param_1")
    public String modelAttributeTest() {
        return "hello world  -------";
    }

    @GetMapping("/class-autowire")
    public String doClassAutowire(@ModelAttribute("param_1") String param_1) {
        return param_1;
    }

    @Cacheable(value = "myCache", key = "#root.targetClass+'['+#root.method+']'+'['+#commonDto.code+']'")
    @GetMapping("/cache")
    public CommonDto redisCache(CommonDto commonDto) {
        return new ResponseDtoBuilder<CommonDto>(CommonDto.class)
                .enumSet(AuthEnums.STATUS_BANNED)
                .data(new HashMap<>())
                .build();
    }
}
