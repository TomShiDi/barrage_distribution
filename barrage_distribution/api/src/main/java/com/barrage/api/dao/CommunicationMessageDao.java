package com.barrage.api.dao;

public class CommunicationMessageDao {
    private String sendingDate;
    private String sendingMessage;
    private String currentDate;
    private int onlineCount;
    private int methodCode;

    public String getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(String sendingDate) {
        this.sendingDate = sendingDate;
    }

    public String getSendingMessage() {
        return sendingMessage;
    }

    public void setSendingMessage(String sendingMessage) {
        this.sendingMessage = sendingMessage;
    }

    public int getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public int getMethodCode() {
        return methodCode;
    }

    public void setMethodCode(int methodCode) {
        this.methodCode = methodCode;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }
}
