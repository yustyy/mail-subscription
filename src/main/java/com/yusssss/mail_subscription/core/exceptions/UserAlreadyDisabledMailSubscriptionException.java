package com.yusssss.mail_subscription.core.exceptions;

import com.yusssss.mail_subscription.core.constants.SentMailMessages;
import com.yusssss.mail_subscription.core.results.ErrorCode;


public class UserAlreadyDisabledMailSubscriptionException extends MailSubscriptionException {

    public UserAlreadyDisabledMailSubscriptionException() {
        super(SentMailMessages.MAIL_SUBSCRIPTION_ALREADY_DISABLED, ErrorCode.MAIL_SUBSCRIPTION_ALREADY_DISABLED);
    }
}