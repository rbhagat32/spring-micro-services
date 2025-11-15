package com.rbhagat32.authservice.entity;

import com.rbhagat32.authservice.dto.UserDTO;
import com.rbhagat32.authservice.enums.EmailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailEntity {

    private UserDTO user;
    private EmailType type;
    private Map<String, String> placeholders;
}