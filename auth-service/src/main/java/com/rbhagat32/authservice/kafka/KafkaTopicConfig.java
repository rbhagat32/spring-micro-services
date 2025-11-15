package com.rbhagat32.authservice.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic welcomeEmailTopic() {
        return new NewTopic("EMAILS", 1, (short) 1);
    }
}