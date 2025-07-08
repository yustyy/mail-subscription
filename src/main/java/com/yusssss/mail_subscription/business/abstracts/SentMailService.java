package com.yusssss.mail_subscription.business.abstracts;

import com.yusssss.mail_subscription.entities.SentMail;

import java.util.List;
import java.util.UUID;

public interface SentMailService {

    SentMail saveSentMail(SentMail sentMail);

    void unsubscribeFromMails(UUID sentMailId);

    List<SentMail> getAllSentMails();

    List<SentMail> getSentMailsByReceiverId(UUID userId);

    SentMail getSentMailById(UUID id);

    void sendMailToSubscribers(SentMail sentMail, boolean isHtmlContent);


}
