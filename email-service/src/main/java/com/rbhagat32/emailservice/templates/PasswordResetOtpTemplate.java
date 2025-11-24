package com.rbhagat32.emailservice.templates;

import com.rbhagat32.emailservice.entity.EmailTemplateEntity;
import com.rbhagat32.emailservice.enums.EmailType;
import com.rbhagat32.emailservice.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordResetOtpTemplate implements CommandLineRunner {

    private final EmailTemplateRepository emailTemplateRepository;

    @Override
    public void run(String... args) {
        if (!emailTemplateRepository.existsByType(EmailType.PASSWORD_RESET_OTP)) {

            EmailTemplateEntity template = new EmailTemplateEntity();
            template.setType(EmailType.PASSWORD_RESET_OTP);
            template.setSubject("Password Reset OTP !");
            template.setBody("""
                    <div style="font-family: 'Segoe UI', Arial, sans-serif; background-color: #f9fafb; padding: 24px; border-radius: 10px; color: #333; line-height: 1.6;">
                        <h2 style="color: #1a202c;">Password Reset OTP !</h2>
                        <p>Hi {{name}},</p>
                    
                        <p>We received a request to reset the password for your account associated with: <strong>{{email}}</strong></p>
                    
                        <p>Your One-Time Password (OTP) is:</p>
                    
                        <div style="font-size: 32px; font-weight: bold; letter-spacing: 6px; margin: 20px 0; color: #2d3748; text-align: center;">
                            {{otp}}
                        </div>
                    
                        <p>This OTP is valid for <strong>10 minutes</strong>. Please do not share it with anyone for security reasons.</p>
                    
                        <p>If you did not request a password reset, you can ignore this email. Your account will remain safe.</p>
                    
                        <p style="margin-top: 20px;">Regards,<br><strong>Spring Micro-Services Team</strong></p>
                    </div>
                    """);

            emailTemplateRepository.save(template);
            System.out.println("Seeded PASSWORD_RESET_OTP template successfully");
        }
    }
}