package com.barrage.webcontroller.serviceImpl;


import com.barrage.api.entity.TomMailProperties;
import com.barrage.api.service.MailService;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @Author TomShiDi
 * @Description 邮件服务实现
 * @Date 2020/3/17
 **/
@Service
public class MailServiceImpl implements MailService {

    private JavaMailSender javaMailSender;

    private TomMailProperties tomMailProperties;

    @Autowired
    public MailServiceImpl(JavaMailSender javaMailSender, TomMailProperties tomMailProperties) {
        this.javaMailSender = javaMailSender;
        this.tomMailProperties = tomMailProperties;
    }


    @Override
    public void sendSimpleMail(String to, String subject, String textContent) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(tomMailProperties.getFrom());
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(textContent);
        javaMailSender.send(simpleMailMessage);
    }

    @Override
    public void sendHtmlMail(String to, String subject, String htmlContent, Map<String, String> attachmentMap) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(tomMailProperties.getFrom());
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(htmlContent, true);
        if (attachmentMap != null) {
            attachmentMap.entrySet().stream().forEach(entrySet -> {
                File file = new File(entrySet.getValue());
                try {
                    if (file.exists()) {
                        mimeMessageHelper.addAttachment(entrySet.getKey(), new FileSystemResource(file));
                    }
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
        }
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void sendTemplateMail(String to, String subject, Map<String, Object> params) throws MessagingException, IOException, TemplateException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(tomMailProperties.getFrom());
        helper.setTo(to);
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_20);
        configuration.setClassForTemplateLoading(this.getClass(), "/templates");
        String htmlContent = FreeMarkerTemplateUtils.processTemplateIntoString(configuration.getTemplate("mailtemplate.ftl"), params);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        javaMailSender.send(mimeMessage);
    }
}
