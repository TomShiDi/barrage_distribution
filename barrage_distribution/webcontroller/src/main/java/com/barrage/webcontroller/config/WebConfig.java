package com.barrage.webcontroller.config;

import com.barrage.webcontroller.utils.BilibiliJsonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author TomShiDi
 * @Date 2020年3月24日18:28:54
 */
@Configuration
@PropertySource({"classpath:application.yml"})
public class WebConfig implements WebMvcConfigurer {

    @Value("${filePath}")
    private String resourceLocation;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/info/**").addResourceLocations("file:" + resourceLocation + "/");
        registry.addResourceHandler("/.well-known/pki-validation/**").addResourceLocations("file:/usr/valid/");
        BilibiliJsonUtil.setFilePath(resourceLocation);
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login.html");
        System.out.println("Config执行");
    }
}
