package com.rbhagat32.authservice.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class UserDTO {

    private String id;
    private String name;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
}