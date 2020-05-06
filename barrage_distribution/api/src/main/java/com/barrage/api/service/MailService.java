package com.barrage.api.service;

import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Map;

/**
 * @Author TomShiDi
 * @Description 邮件发送服务接口
 * @Date 2020/3/17
 **/
public interface MailService {
    /**
     * 发送简单的文字邮件
     *
     * @param to          收件人
     * @param subject     标题
     * @param textContent 内容正文
     */
    public abstract void sendSimpleMail(String to, String subject, String textContent);

    /**
     * 发送html邮件，带附件
     *
     * @param to            收件人
     * @param subject       标题
     * @param htmlContent   内容正文
     * @param attachmentMap 附件
     */
    public abstract void sendHtmlMail(String to, String subject, String htmlContent, Map<String, String> attachmentMap) throws MessagingException;

    /**
     * 发送模板邮件 freemarket
     *
     * @param to      收件人
     * @param subject 标题
     * @param params  模板参数
     */
    public abstract void sendTemplateMail(String to, String subject, Map<String, Object> params) throws MessagingException, IOException, TemplateException;
}
