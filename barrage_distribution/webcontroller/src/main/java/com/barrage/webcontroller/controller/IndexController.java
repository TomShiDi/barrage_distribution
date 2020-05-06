package com.barrage.webcontroller.controller;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

import static com.barrage.webcontroller.config.AliPayConfig.*;


@Controller
@RestController("/index")
public class IndexController {
    @GetMapping("/index")
    public ModelAndView showIndex() {

        return new ModelAndView("index");
    }

    @GetMapping("/alipay")
    public void payTest(HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws IOException, AlipayApiException {
        AlipayClient alipayClient = new DefaultAlipayClient(SERVICE_UTL, APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA"); //获得初始化的AlipayClient
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();//创建API对应的request
//        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();//创建API对应的request
//        AlipayOpenPublicTemplateMessageIndustryModifyRequest alipayRequest = new AlipayOpenPublicTemplateMessageIndustryModifyRequest();
        alipayRequest.setReturnUrl("http://nmah69.natappfree.cc/callback");
        alipayRequest.setNotifyUrl("http://nmah69.natappfree.cc/notify");//在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"2015032001010100" + new Random().nextInt(10) + "\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }" +
                "  }");//填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); //调用SDK生成表单
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + CHARSET);
        httpResponse.getWriter().write(form);//直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();
//        AlipayOpenPublicTemplateMessageIndustryModifyResponse response = alipayClient.execute(alipayRequest);

    }

    @GetMapping("/callback")
    public String returnMapping(HttpServletRequest request, HttpServletResponse response) {
        return "success";
    }

    @GetMapping("/notify")
    public String notifyMapping() {
        return "notify";
    }

    //TODO 一级请求响应后，请求被拦截，不再向下传递
//    @RequestMapping(name = "")
//    public ModelAndView nonePathRequest() {
//
//        return new ModelAndView("index");
//    }
}
