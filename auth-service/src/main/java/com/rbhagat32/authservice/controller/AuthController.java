package com.rbhagat32.authservice.controller;

import com.rbhagat32.authservice.dto.AuthResponseDTO;
import com.rbhagat32.authservice.dto.LoginRequestDTO;
import com.rbhagat32.authservice.dto.RegisterRequestDTO;
import com.rbhagat32.authservice.dto.UserDTO;
import com.rbhagat32.authservice.service.AuthService;
import com.rbhagat32.authservice.utils.CookieUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO body) {

        AuthResponseDTO registerResponse = authService.register(body);
        ResponseCookie cookie = cookieUtil.setCookie(registerResponse, "TOKEN");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO body) {
        AuthResponseDTO loginResponse = authService.login(body);
        ResponseCookie cookie = cookieUtil.setCookie(loginResponse, "TOKEN");

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        ResponseCookie cookie = cookieUtil.removeCookie("TOKEN");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged out successfully");
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getLoggedInUser3() {
        return ResponseEntity.ok(authService.getLoggedInUser());
    }
}