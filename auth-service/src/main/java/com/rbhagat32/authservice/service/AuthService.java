package com.rbhagat32.authservice.service;

import com.rbhagat32.authservice.dto.AuthResponseDTO;
import com.rbhagat32.authservice.dto.LoginRequestDTO;
import com.rbhagat32.authservice.dto.RegisterRequestDTO;
import com.rbhagat32.authservice.dto.UserDTO;
import com.rbhagat32.authservice.entity.UserEntity;
import com.rbhagat32.authservice.kafka.EmailProducer;
import com.rbhagat32.authservice.repository.UserRepository;
import com.rbhagat32.authservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;
    private final EmailProducer emailProducer;

    public AuthResponseDTO register(RegisterRequestDTO body) {
        if (userRepository.findByEmail(body.getEmail()).isPresent()) {
            throw new BadCredentialsException("Email is already registered");
        }

        UserEntity user = new UserEntity();
        user.setName(body.getName());
        user.setEmail(body.getEmail());
        user.setPassword(passwordEncoder.encode(body.getPassword()));

        UserEntity savedUser = userRepository.save(user);

        String token = jwtUtil.generateToken(savedUser);

        emailProducer.produceWelcomeEmail(savedUser);
        return new AuthResponseDTO(token, modelMapper.map(savedUser, UserDTO.class));
    }

    public AuthResponseDTO login(LoginRequestDTO body) {
        UserEntity user = userRepository.findByEmail(body.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Invalid email"));

        if (!passwordEncoder.matches(body.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDTO(token, modelMapper.map(user, UserDTO.class));
    }

    public UserDTO getLoggedInUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("User is not authenticated");
        }

        UserEntity loggedInUser = (UserEntity) authentication.getPrincipal();
        return modelMapper.map(loggedInUser, UserDTO.class);
    }
}