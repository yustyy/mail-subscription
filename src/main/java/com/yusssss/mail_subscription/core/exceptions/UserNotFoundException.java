package com.yusssss.mail_subscription.core.exceptions;


import com.yusssss.mail_subscription.core.constants.UserMessages;
import com.yusssss.mail_subscription.core.results.ErrorCode;

public class UserNotFoundException extends MailSubscriptionException {

    public UserNotFoundException() {
        super(UserMessages.USER_NOT_FOUND, ErrorCode.USER_NOT_FOUND);
    }
}