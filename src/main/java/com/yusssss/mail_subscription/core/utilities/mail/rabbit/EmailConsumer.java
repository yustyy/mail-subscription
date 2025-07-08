package com.yusssss.mail_subscription.core.utilities.mail.rabbit;

import com.yusssss.mail_subscription.core.config.RabbitMQConfig;
import com.yusssss.mail_subscription.core.utilities.mail.EmailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class EmailConsumer {

    private final EmailService emailService;


    public EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }



    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void processEmailMessage(@Payload EmailMessage emailMessage){
        try{
            boolean success = emailService.sendMail(
                    emailMessage.getTo(),
                    emailMessage.getSubject(),
                    emailMessage.getBody()
            );

            if (success){
                System.out.println("Email sent successfully to: " + emailMessage.getTo());
            } else {
                System.err.println("Failed to send email to: " + emailMessage.getTo());
            }
        }catch (Exception e){
            //will be logging these errors in the future
            System.out.println("Error processing email message: " + e.getMessage());
        }
    }
}
