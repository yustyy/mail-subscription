package com.yusssss.mail_subscription.dataAccess;

import com.yusssss.mail_subscription.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserDao extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    List<User> findAllBySubscriptionEnabled(boolean isSubscriptionEnabled);
}
