package com.yusssss.mail_subscription.business.concretes;

import com.yusssss.mail_subscription.business.abstracts.SentMailService;
import com.yusssss.mail_subscription.business.abstracts.UserService;
import com.yusssss.mail_subscription.core.exceptions.NoSubscribersFoundException;
import com.yusssss.mail_subscription.core.exceptions.SentMailNotFoundException;
import com.yusssss.mail_subscription.core.exceptions.UserAlreadyDisabledMailSubscriptionException;
import com.yusssss.mail_subscription.core.utilities.mail.EmailService;
import com.yusssss.mail_subscription.dataAccess.SentMailDao;
import com.yusssss.mail_subscription.entities.SentMail;
import com.yusssss.mail_subscription.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SentMailManager implements SentMailService {

    private final SentMailDao sentMailDao;
    private final UserService userService;
    private final EmailService emailService;

    public SentMailManager(SentMailDao sentMailDao, UserService userService, EmailService emailService) {
        this.sentMailDao = sentMailDao;
        this.userService = userService;
        this.emailService = emailService;
    }

    @Override
    public SentMail saveSentMail(SentMail sentMail) {
        return sentMailDao.save(sentMail);
    }

    @Override
    public void unsubscribeFromMails(UUID sentMailId) {
        var sentMail = getSentMailById(sentMailId);

        var user = sentMail.getReceiver();
        if (!user.isSubscriptionEnabled()){
            throw new UserAlreadyDisabledMailSubscriptionException();
        }

        user.setSubscriptionEnabled(false);

        userService.updateUser(user);
        sentMail.setUnsubscribed(true);
        sentMailDao.save(sentMail);
    }

    @Override
    public List<SentMail> getAllSentMails() {
        return sentMailDao.findAll();
    }

    @Override
    public List<SentMail> getSentMailsByReceiverId(UUID userId) {
        return sentMailDao.findAllByReceiverId(userId);
    }

    @Override
    public SentMail getSentMailById(UUID id) {
        return sentMailDao.findById(id)
                .orElseThrow(SentMailNotFoundException::new);
    }

    @Override
    public void sendMailToSubscribers(SentMail sentMail, boolean isHtmlContent) {
        var sender = userService.getAuthenticatedUser();
        var receivers = userService.getUsersBySubscriptionEnabled(true);
        if (receivers.isEmpty()) {
            throw new NoSubscribersFoundException();
        }

        for (User receiver : receivers) {
            SentMail personalizedMail = new SentMail();
            personalizedMail.setSender(sender);
            personalizedMail.setReceiver(receiver);
            personalizedMail.setUnsubscribed(false);

            personalizedMail = sentMailDao.save(personalizedMail);

            String unsubscribeUrl = "http://192.168.1.10:8080/api/sentMails/unsubscribeFromMails/" + personalizedMail.getId();

            String firstName = capitalize(receiver.getFirstName());
            String lastName = capitalize(receiver.getLastName());

            String personalizedSubject = sentMail.getSubject()
                    .replace("{first_name}", firstName)
                    .replace("{last_name}", lastName)
                    .replace("{email}", receiver.getEmail())
                    .replace("{username}", receiver.getUsername() != null ? receiver.getUsername() : "")
                    .replace("{sent_mail_id}", personalizedMail.getId().toString());

            String personalizedContent = sentMail.getContent()
                    .replace("{first_name}", firstName)
                    .replace("{last_name}", lastName)
                    .replace("{email}", receiver.getEmail())
                    .replace("{username}", receiver.getUsername() != null ? receiver.getUsername() : "")
                    .replace("{sent_mail_id}", personalizedMail.getId().toString());

            if (!isHtmlContent) {
                personalizedContent = "<pre style=\"font-family:inherit;\">" + personalizedContent + "</pre>";
                personalizedContent += "<br><br><a href=\"" + unsubscribeUrl + "\" style=\"color:#ff4d4f;text-decoration:underline;font-weight:bold;\">Abonelikten çık | Unsubscribe</a>";
            } else {
                personalizedContent +=
                        "<div style=\"background:#fff;color:#000;padding:20px 0;text-align:center;margin-top:24px;\">" +
                                "<a href=\"" + unsubscribeUrl + "\" style=\"color:#ff4d4f;text-decoration:underline;font-weight:bold;\">Abonelikten çık | Unsubscribe</a>" +
                                "</div>";
            }

            personalizedMail.setSubject(personalizedSubject);
            personalizedMail.setContent(personalizedContent);

            emailService.sendEmailAsync(receiver.getEmail(), personalizedSubject, personalizedContent);

            sentMailDao.save(personalizedMail);
        }
    }

    private String capitalize(String value) {
        if (value == null || value.isEmpty()) return "";
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }
}
