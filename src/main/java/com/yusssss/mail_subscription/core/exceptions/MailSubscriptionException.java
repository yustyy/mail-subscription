package com.yusssss.mail_subscription.core.exceptions;


import com.yusssss.mail_subscription.core.results.ErrorCode;

public class MailSubscriptionException extends RuntimeException {
    private final String messageKey;
    private final ErrorCode errorCode;

    protected MailSubscriptionException(String messageKey, ErrorCode errorCode) {
        super(messageKey);
        this.messageKey = messageKey;
        this.errorCode = errorCode;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
