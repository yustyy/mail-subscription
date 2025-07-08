package com.yusssss.mail_subscription.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "sent_mails")
public class SentMail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @Column(name = "subject")
    private String subject;

    @Lob
    @Column(columnDefinition = "TEXT", name = "content")
    private String content;

    @Column(name = "unsubscribed")
    private boolean unsubscribed;

    /*
    @OneToMany(mappedBy = "sentMail", fetch = FetchType.LAZY)
    private List<UnsubscribeMail> unsubscribeMails;

     */


}
