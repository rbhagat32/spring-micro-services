package com.rbhagat32.emailservice.entity;

import com.rbhagat32.emailservice.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "emails")
public class EmailTemplateEntity {

    @Id
    private String id = UUID.randomUUID().toString();

    @Indexed(unique = true)
    private EmailType type;

    private String subject;
    private String body;
}