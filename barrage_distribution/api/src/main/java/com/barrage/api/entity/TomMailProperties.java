package com.barrage.api.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * @Author TomShiDi
 * @Description
 * @Date 2020/3/17
 **/
@Component
@ConfigurationProperties(prefix = "mail")
public class TomMailProperties {
    private String from;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
