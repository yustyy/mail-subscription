package com.yusssss.mail_subscription.core.utilities.mail;

public interface EmailService {

    boolean sendMail(String to, String subject, String body);

    void sendEmailAsync(String to, String subject, String body);

}
