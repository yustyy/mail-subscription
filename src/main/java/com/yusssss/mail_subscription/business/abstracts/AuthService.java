package com.yusssss.mail_subscription.business.abstracts;


import com.yusssss.mail_subscription.entities.User;

public interface AuthService {

    String login(String email, String password);

    void register(User user);


}
