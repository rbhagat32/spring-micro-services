package com.rbhagat32.emailservice.kafka;

import com.rbhagat32.emailservice.entity.EmailEntity;
import com.rbhagat32.emailservice.entity.EmailTemplateEntity;
import com.rbhagat32.emailservice.repository.EmailTemplateRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final JavaMailSender javaMailSender;
    private final EmailTemplateRepository emailTemplateRepository;

    @KafkaListener(topics = "EMAILS", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeEmail(EmailEntity emailEntity) {
        try {
            EmailTemplateEntity template = emailTemplateRepository.findByType(emailEntity.getType())
                    .orElseThrow(() -> new RuntimeException("No template found for type: " + emailEntity.getType()));

            String renderedBody = populateTemplate(template.getBody(), emailEntity.getPlaceholders());

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setTo(emailEntity.getUser().getEmail());
            helper.setSubject(template.getSubject());
            helper.setText(renderedBody, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.err.println("Failed to send email to: " + emailEntity.getUser().getEmail());
        }
    }

    private String populateTemplate(String template, Map<String, String> placeholders) {
        if (placeholders == null || placeholders.isEmpty()) {
            return template;
        }

        String rendered = template;
        for (Map.Entry<String, String> entry : placeholders.entrySet()) {
            String key = "{{" + entry.getKey() + "}}";
            rendered = rendered.replace(key, entry.getValue());
        }
        return rendered;
    }
}