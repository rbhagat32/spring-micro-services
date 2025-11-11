package com.rbhagat32.authservice.controller;

import com.rbhagat32.authservice.dto.AuthResponseDTO;
import com.rbhagat32.authservice.dto.LoginRequestDTO;
import com.rbhagat32.authservice.dto.RegisterRequestDTO;
import com.rbhagat32.authservice.dto.UserDTO;
import com.rbhagat32.authservice.security.JwtUtil;
import com.rbhagat32.authservice.service.AuthService;
import com.rbhagat32.authservice.utils.CookieUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CookieUtil cookieUtil;
    private final JwtUtil jwtUtil;

    @GetMapping("/validate-token")
    public ResponseEntity<Void> validateToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String token = authHeader.substring(7);
        try {
            jwtUtil.validateToken(token);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO body) {

        AuthResponseDTO registerResponse = authService.register(body);
        ResponseCookie cookie = cookieUtil.setCookie(registerResponse, "TOKEN");

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + registerResponse.getToken())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(registerResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO body) {
        AuthResponseDTO loginResponse = authService.login(body);
        ResponseCookie cookie = cookieUtil.setCookie(loginResponse, "TOKEN");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.getToken())
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(loginResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("You are already Logged Out !");
        }

        ResponseCookie cookie = cookieUtil.removeCookie("TOKEN");

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body("Logged Out Successfully !");
    }

    @GetMapping("/get-user")
    public ResponseEntity<UserDTO> getLoggedInUser3(Authentication authentication) {
        return ResponseEntity.ok(authService.getLoggedInUser(authentication));
    }
}