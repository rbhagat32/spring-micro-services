package com.rbhagat32.emailservice.templates;

import com.rbhagat32.emailservice.entity.EmailTemplateEntity;
import com.rbhagat32.emailservice.enums.EmailType;
import com.rbhagat32.emailservice.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEmailTemplate implements CommandLineRunner {

    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    public void run(String... args) {
        if (!emailTemplateRepository.existsByType(EmailType.NOTIFICATION)) {

            EmailTemplateEntity notificationTemplate = new EmailTemplateEntity();
            notificationTemplate.setType(EmailType.NOTIFICATION);
            notificationTemplate.setSubject("You Have a New Notification !");
            notificationTemplate.setBody("""
                    <div style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #f9fafb; padding: 24px; border-radius: 10px; color: #333; line-height: 1.6;">
                        <h2 style="color: #1a202c;">New Notification</h2>
                        <p>Hi {{name}},</p>
                    
                        <p>You have a new update:</p>
                    
                        <div style="background-color: #edf2f7; padding: 16px; border-radius: 8px; margin: 20px 0; color: #2d3748;">
                            {{message}}
                        </div>
                    
                        <p>If the notification requires action, please log into your account to continue.</p>
                    
                        <p style="margin-top: 20px;">Regards,<br><strong>Micro-Services Team</strong></p>
                    </div>
                    """);

            emailTemplateRepository.save(notificationTemplate);
            System.out.println("Seeded NOTIFICATION template successfully");
        } else {
            System.out.println("NOTIFICATION template already exists");
        }
    }
}