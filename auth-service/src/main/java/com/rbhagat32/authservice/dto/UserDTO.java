package com.rbhagat32.authservice.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class UserDTO {

    private String id;
    private String name;
    private String email;
    private Set<String> roles;
    private Instant createdAt;
    private Instant updatedAt;
}