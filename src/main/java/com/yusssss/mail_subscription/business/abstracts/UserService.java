package com.yusssss.mail_subscription.business.abstracts;

import com.yusssss.mail_subscription.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.UUID;

public interface UserService extends UserDetailsService {

    List<User> getAllUsers();

    User getUserById(UUID id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User saveUser(User user);

    User deleteUser(UUID id);

    User updateUser(User user);

    User getAuthenticatedUser();

    List<User> getUsersBySubscriptionEnabled(boolean isSubscriptionEnabled);

}
