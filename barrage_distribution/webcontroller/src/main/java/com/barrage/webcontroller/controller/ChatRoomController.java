package com.barrage.webcontroller.controller;


import com.barrage.webcontroller.utils.CookieUtil;
import com.barrage.api.dao.ChatRoomDao;
import com.barrage.api.enums.ChatRoomEnum;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
@RequestMapping("/chat")
public class ChatRoomController {

    /**
     * 用户注册nickname，只是简单的验证然后将nickname加到response的cookie中，失效时间是1小时
     *
     * @param nickName
     * @return
     */
    @GetMapping("/register")
    public String register(@RequestParam(name = "nickName") String nickName) {
        Gson gson = new Gson();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        CookieUtil.setCookieNickName(attributes.getResponse(), nickName);
        return gson.toJson(new ChatRoomDao()
                .message("success")
                .code(ChatRoomEnum.SUCCESS.getCode())
                .confirmNickName(nickName));//返回处理结果
    }

}
