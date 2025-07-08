package com.yusssss.mail_subscription.core.exceptions;

import com.yusssss.mail_subscription.core.constants.SentMailMessages;
import com.yusssss.mail_subscription.core.results.ErrorCode;

public class SentMailNotFoundException extends MailSubscriptionException {

    public SentMailNotFoundException() {
        super(SentMailMessages.SENT_MAIL_NOT_FOUND, ErrorCode.SENT_MAIL_NOT_FOUND);
    }
}
