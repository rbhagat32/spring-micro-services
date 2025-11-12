package com.rbhagat32.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String id;
    private String name;
    private String email;
    private Instant createdAt;
    private Instant updatedAt;
}