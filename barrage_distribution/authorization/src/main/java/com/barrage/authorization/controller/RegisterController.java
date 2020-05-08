package com.barrage.authorization.controller;

import com.barrage.authorization.redis.DefaultRedisComponent;
import com.barrage.authorization.utils.RandomKeyUtil;
import com.barrage.api.builders.ResponseDtoBuilder;
import com.barrage.api.dto.RegisterResponseDto;
import com.barrage.api.entity.UserInfo;
import com.barrage.api.enums.AuthEnums;
import com.barrage.api.enums.BarrageExceptionEnum;
import com.barrage.api.service.MailService;
import com.barrage.api.service.UserInfoService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Controller;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/4/15
 **/
@Controller()
@RequestMapping("/register")
@CrossOrigin(origins = {"https://120.77.222.242"})
public class RegisterController {

    private final Logger logger = LoggerFactory.getLogger(RegisterController.class);

    @Reference
    private UserInfoService userInfoService;

    private KafkaTemplate<String, Object> kafkaTemplate;

    private DefaultRedisComponent defaultRedisComponent;

    @Reference
    private MailService mailService;

    @Value("${register-topic}")
    private String registerTopic;

    @Value("${auth-url}")
    private String authUrl;

    @Value("${index-url}")
    private String indexUrl;

    @Autowired
    public RegisterController(KafkaTemplate<String, Object> kafkaTemplate,
                              DefaultRedisComponent defaultRedisComponent) {
        this.kafkaTemplate = kafkaTemplate;
        this.defaultRedisComponent = defaultRedisComponent;
    }

    @ResponseBody
    @RequestMapping(value = "/do", method = RequestMethod.POST)
    public RegisterResponseDto preRegister(@Valid UserInfo userInfo, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return (RegisterResponseDto) new ResponseDtoBuilder<>(RegisterResponseDto.class)
                    .message(bindingResult.getFieldError().getDefaultMessage())
                    .code(BarrageExceptionEnum.LOGIN_INFO_INVALID.getCode())
                    .build();
        }
        UserInfo userInfoDto = new UserInfo();
        BeanUtils.copyProperties(userInfo, userInfoDto, "userStatus");
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(registerTopic, userInfoDto);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
            @Override
            public void onFailure(Throwable ex) {
                logger.warn("kafka消息发送失败: {}", ex.getMessage());
                throw new RuntimeException(ex);
            }

            @Override
            public void onSuccess(SendResult<String, Object> result) {
                logger.info("kafka消息发送成功: {}", result.toString());
            }
        });
        return (RegisterResponseDto) new ResponseDtoBuilder<>(RegisterResponseDto.class)
                .enumSet(BarrageExceptionEnum.REGISTER_SUCCESS)
                .build();
    }

    @GetMapping("/activate")
    public ModelAndView activateAccount(@RequestParam(name = "email") String userEmail,
                                        @RequestParam(name = "authCode") String authCode) {
        String redisValue = defaultRedisComponent.getStringValue(userEmail);
        if (userEmail != null && authCode != null) {
            if (authCode.equals(redisValue)) {
                int result = userInfoService.updateUserAccountStatus(userEmail, AuthEnums.STATUS_ACTIVATION.getCode());
                if (result == 1) {
                    Map<String, Object> params = new HashMap<>(4);
                    params.put("indexUrl", indexUrl);
                    return new ModelAndView("common/success", params);
                }else {
                    Map<String, Object> params = new HashMap<>(4);
                    params.put("indexUrl", indexUrl);
                    return new ModelAndView("common/failed", params);
                }
            }
        }
        Map<String, Object> params = new HashMap<>(4);
        params.put("indexUrl", indexUrl);
        return new ModelAndView("common/failed", params);
    }

    @KafkaListener(topics = "topic_register", id = "register_1")
    public void register(UserInfo userInfo) throws Exception{
        userInfoService.saveUserInfo(userInfo);
        Map<String, Object> params = new HashMap<>(4);
        String authCode = RandomKeyUtil.getRandomNum();
        defaultRedisComponent.setAsKeyValue(userInfo.getUserEmail(), authCode,300);
        params.put("userEmail", userInfo.getUserEmail());
        params.put("authCode", authCode);
        params.put("authUrl", authUrl);
        mailService.sendTemplateMail(userInfo.getUserEmail(),"验证码",params);
    }
}
