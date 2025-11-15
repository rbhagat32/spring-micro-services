package com.rbhagat32.emailservice.kafka;

import com.rbhagat32.emailservice.repository.EmailRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    @KafkaListener(topics = "EMAILS", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeWelcomeEmail(String userId) {
        System.out.println("Welcome Email Consumed from Kafka: " + userId);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

            helper.setTo("raghav@gmail.com");

//            EmailTemplateEntity emailTemplate = emailRepository.findByType();

            helper.setSubject("SUBJECT");
            helper.setText("BODY", true);

            javaMailSender.send(message);
            System.out.println("Welcome Email sent to: " + "raghav@gmail.com");
        } catch (MessagingException e) {
            System.err.println("Failed to send welcome email to: " + "raghav@gmail.com");
        }
    }
}