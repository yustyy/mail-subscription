package com.yusssss.mail_subscription.core.exceptions;

import com.yusssss.mail_subscription.core.constants.SentMailMessages;
import com.yusssss.mail_subscription.core.results.ErrorCode;

public class NoSubscribersFoundException extends MailSubscriptionException {

    public NoSubscribersFoundException() {
        super(SentMailMessages.NO_SUBSCRIBERS_FOUND, ErrorCode.NO_SUBSCRIBERS_FOUND);
    }
}
