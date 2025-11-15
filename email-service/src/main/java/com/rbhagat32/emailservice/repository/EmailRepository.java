package com.rbhagat32.emailservice.repository;

import com.rbhagat32.emailservice.entity.EmailTemplateEntity;
import com.rbhagat32.emailservice.enums.EmailType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends MongoRepository<EmailTemplateEntity, String> {
    EmailTemplateEntity findByType(EmailType type);
}