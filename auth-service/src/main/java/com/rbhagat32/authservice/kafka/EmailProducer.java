package com.rbhagat32.authservice.kafka;

import com.rbhagat32.authservice.dto.UserDTO;
import com.rbhagat32.authservice.entity.EmailEntity;
import com.rbhagat32.authservice.entity.UserEntity;
import com.rbhagat32.authservice.enums.EmailType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailProducer {

    private final KafkaTemplate<String, EmailEntity> kafkaTemplate;
    private final ModelMapper modelMapper;

    public void produceWelcomeEmail(UserEntity user) {
        EmailEntity welcomeEmail = new EmailEntity(
                modelMapper.map(user, UserDTO.class),
                EmailType.WELCOME_EMAIL,
                Map.of("name", user.getName(), "email", user.getEmail())
        );

        kafkaTemplate.send("EMAILS", welcomeEmail);
    }
}