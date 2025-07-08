package com.yusssss.mail_subscription.core.utilities.mail;

import com.yusssss.mail_subscription.core.utilities.mail.rabbit.EmailProducer;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
public class JavaMailSenderManager implements EmailService{

    private final JavaMailSender javaMailsender;
    private final EmailProducer emailProducer;

    public JavaMailSenderManager(JavaMailSender javaMailsender, EmailProducer emailProducer) {
        this.javaMailsender = javaMailsender;
        this.emailProducer = emailProducer;
    }

    @Value("${spring.mail.username}")
    private String from;


    @Override
    public boolean sendMail(String to, String subject, String body) {

        MimeMessage mimeMessage = javaMailsender.createMimeMessage();

        try{
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);
            javaMailsender.send(mimeMessage);
            return true;
        }catch (Exception e){
           throw new RuntimeException("Failed to send email: " + e.getMessage(), e);
        }


    }

    @Override
    public void sendEmailAsync(String to, String subject, String body) {
        emailProducer.sendEmailToQueue(to, subject, body);
    }
}
