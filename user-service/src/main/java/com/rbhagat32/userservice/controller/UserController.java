package com.rbhagat32.userservice.controller;

import com.rbhagat32.userservice.dto.UserDTO;
import com.rbhagat32.userservice.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getLoggedInUser(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getLoggedInUser(request));
    }
}