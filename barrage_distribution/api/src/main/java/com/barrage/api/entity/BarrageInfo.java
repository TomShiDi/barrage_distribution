package com.barrage.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class BarrageInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer barrageId;

    @NotNull(message = "发送者Id不能为空")
    private Integer barrageSenderId;

    @NotEmpty(message = "弹幕内容不能为空")
    private String content;

    private Integer starNum = 0;

    private Integer speed = 12;

    private String color = "white";

    private Integer textSize = 20;

    private Integer road = 0;

    private Date barrageSendTime;


    public Integer getBarrageId() {
        return barrageId;
    }

    public void setBarrageId(Integer barrageId) {
        this.barrageId = barrageId;
    }

    public Integer getBarrageSenderId() {
        return barrageSenderId;
    }

    public void setBarrageSenderId(Integer barrageSenderId) {
        this.barrageSenderId = barrageSenderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStarNum() {
        return starNum;
    }

    public void setStarNum(Integer starNum) {
        this.starNum = starNum;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getRoad() {
        return road;
    }

    public void setRoad(Integer road) {
        this.road = road;
    }

    public Integer getTextSize() {
        return textSize;
    }

    public void setTextSize(Integer textSize) {
        this.textSize = textSize;
    }

    public Date getBarrageSendTime() {
        return barrageSendTime;
    }

    public void setBarrageSendTime(Date barrageSendTime) {
        this.barrageSendTime = barrageSendTime;
    }
}
