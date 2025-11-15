package com.rbhagat32.emailservice.repository;

import com.rbhagat32.emailservice.entity.EmailTemplateEntity;
import com.rbhagat32.emailservice.enums.EmailType;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EmailTemplateRepository extends MongoRepository<EmailTemplateEntity, String> {
    Optional<EmailTemplateEntity> findByType(EmailType type);

    boolean existsByType(EmailType type);
}