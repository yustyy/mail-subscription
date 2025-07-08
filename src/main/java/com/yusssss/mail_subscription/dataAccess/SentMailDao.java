package com.yusssss.mail_subscription.dataAccess;

import com.yusssss.mail_subscription.entities.SentMail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SentMailDao extends JpaRepository<SentMail, UUID> {
    List<SentMail> findAllByReceiverId(UUID userId);

}
