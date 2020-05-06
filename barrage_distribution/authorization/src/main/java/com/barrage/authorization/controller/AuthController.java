package com.barrage.authorization.controller;

import com.barrage.authorization.redis.DefaultRedisComponent;
import com.barrage.authorization.utils.CookieUtil;
import com.barrage.authorization.utils.RandomKeyUtil;
import com.barrage.api.builders.ResponseDtoBuilder;
import com.barrage.api.dao.AuthResponseDto;
import com.barrage.api.dto.LoginInfoDto;
import com.barrage.api.entity.UserInfo;
import com.barrage.api.enums.AuthEnums;
import com.barrage.api.enums.BarrageExceptionEnum;
import com.barrage.api.service.UserInfoService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/25
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Reference
    private UserInfoService userInfoService;

    @Autowired
    private DefaultRedisComponent defaultRedisComponent;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${login.userKey}")
    private String userKey;

    private String SESSON_USERID = "userId";



    /**
     * 登录操作的前置方法
     * 消息队列生产者
     * @param loginInfoDto 登录信息
     * @param bindingResult 校验类
     * @return 返回处理结果
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public AuthResponseDto preLogin(@Valid LoginInfoDto loginInfoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(BarrageExceptionEnum.LOGIN_INFO_INVALID)
                    .build();
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        int result = judge(loginInfoDto,attributes.getRequest());
        if (result == AuthEnums.FALSE.getCode()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(AuthEnums.INCORRECT_ACCOUNT_INFO)
                    .data(loginInfoDto)
                    .build();
        }

        if (result == AuthEnums.STATUS_NO_ACTIVATION.getCode()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(AuthEnums.STATUS_NO_ACTIVATION)
                    .data(loginInfoDto)
                    .build();
        }

        if (result == AuthEnums.STATUS_BANNED.getCode()) {
            return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                    .enumSet(AuthEnums.STATUS_BANNED)
                    .data(loginInfoDto)
                    .build();
        }


        String loginToken = RandomKeyUtil.getUniqueUuid();
        CookieUtil.setLoginCookie(attributes.getResponse(), loginInfoDto.getUserName(), loginToken);

//        DigestUtils.md5Digest((userKey + userInfo.getUserPassword()).getBytes());
        defaultRedisComponent.setAsKeyValue(loginInfoDto.getUserName(), loginToken);
//        SendResult<String, Object> result = null;
//        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send("login_topic", loginInfoDto);
//        future.addCallback(new ListenableFutureCallback<SendResult<String, Object>>() {
//            @Override
//            public void onFailure(Throwable ex) {
//                logger.warn("消息队列添加失败: {}", ex.getMessage());
//            }
//
//            @Override
//            public void onSuccess(SendResult<String, Object> result) {
//                logger.info("消息队列添加成功: {}", result.toString());
//            }
//        });
//        try {
//            result = future.get(5000, TimeUnit.MILLISECONDS);
//        } catch (InterruptedException | ExecutionException | TimeoutException e) {
//            e.printStackTrace();
//        }
        return (AuthResponseDto) new ResponseDtoBuilder<>(AuthResponseDto.class)
                .data(loginInfoDto)
                .enumSet(BarrageExceptionEnum.LOGIN_SUCCESS)
                .build();
    }

    /**
     * 消息队列消费方法
     * 登录校验
     * @param loginInfoDto 登录信息数据类
     */
//    @KafkaListener(id = "login_1",topics = "login_topic")
//    public void login(LoginInfoDto loginInfoDto) {
//        if (!judge(loginInfoDto)) {
//            return;
//        }
//
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        String loginToken = RandomKeyUtil.getUniqueUuid();
//        CookieUtil.setLoginCookie(attributes.getResponse(), loginInfoDto.getUserName(), loginToken);
//
////        DigestUtils.md5Digest((userKey + userInfo.getUserPassword()).getBytes());
//        defaultRedisComponent.setAsKeyValue(loginInfoDto.getUserName(), loginToken);
//    }

    private int judge(LoginInfoDto loginInfoDto, HttpServletRequest request) {
        UserInfo origin = userInfoService.findByUserEmail(loginInfoDto.getUserName());
        if (origin == null) {
            return AuthEnums.FALSE.getCode();
        }
        if(!(origin.getUserEmail().equals(loginInfoDto.getUserName())
                && origin.getUserPassword().equals(loginInfoDto.getPassword()))){
            return AuthEnums.FALSE.getCode();
        }
        if (AuthEnums.STATUS_NO_ACTIVATION.getCode().equals(origin.getUserStatus())) {
            return AuthEnums.STATUS_NO_ACTIVATION.getCode();
        }
        if (AuthEnums.STATUS_BANNED.getCode().equals(origin.getUserStatus())) {
            return AuthEnums.STATUS_BANNED.getCode();
        }
        request.getSession().setAttribute(SESSON_USERID, origin.getUserId());
        return AuthEnums.TRUE.getCode();
    }
}
