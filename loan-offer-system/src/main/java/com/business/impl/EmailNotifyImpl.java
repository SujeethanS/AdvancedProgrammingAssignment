package com.business.impl;

import com.business.EmailNotify;
import com.dto.user.request.EmailReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class EmailNotifyImpl implements EmailNotify {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public String sendEmail(EmailReq emailReq) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom(sender);
            simpleMailMessage.setTo(emailReq.getReceiver());
            simpleMailMessage.setText(emailReq.getMessageBody());
            simpleMailMessage.setSubject(emailReq.getSubject());

            javaMailSender.send(simpleMailMessage);

        }catch (Exception exception){
            logger.info("sendEmail-exception--------------->"+exception.toString());
        }
        return "Email sent successfully";
    }
}
