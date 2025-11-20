package com.rbhagat32.emailservice.templates;

import com.rbhagat32.emailservice.entity.EmailTemplateEntity;
import com.rbhagat32.emailservice.enums.EmailType;
import com.rbhagat32.emailservice.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WelcomeEmailTemplate implements CommandLineRunner {

    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    public void run(String... args) {
        if (!emailTemplateRepository.existsByType(EmailType.WELCOME_EMAIL)) {

            EmailTemplateEntity template = new EmailTemplateEntity();
            template.setType(EmailType.WELCOME_EMAIL);
            template.setSubject("Welcome to Micro-Services !");
            template.setBody("""
                    <div style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #f9fafb; padding: 24px; border-radius: 10px; color: #333; line-height: 1.6;">
                        <h2 style="color: #1a202c;">Welcome to Micro-Services !</h2>
                        <p>Hi {{name}},</p>
                        <p>You have successfully registered using: <strong>{{email}}</strong></p>
                    
                        <p>We’re thrilled to have you join our growing community !</p>
                        <p>Here’s what you can do next:</p>
                    
                        <ul style="padding-left: 20px;">
                            <li>📘 Check out our quick-start guide to learn the basics.</li>
                            <li>✨ Explore your dashboard and personalize your profile.</li>
                            <li>💬 Connect and collaborate with other developers.</li>
                        </ul>
                    
                        <p>If you ever need help, our support team is just a click away.</p>
                    
                        <p style="margin-top: 24px;">Thanks again for signing up — we can’t wait to see what you build with <strong>Micro-Services</strong> !</p>
                    
                        <p style="margin-top: 20px;">Regards,<br><strong>Micro-Services Team</strong></p>
                    </div>
                    """);

            emailTemplateRepository.save(template);
            System.out.println("Seeded WELCOME_EMAIL template successfully");
        }
    }
}