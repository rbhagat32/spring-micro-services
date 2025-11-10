package com.rbhagat32.authservice.service;

import com.rbhagat32.authservice.dto.AuthResponseDTO;
import com.rbhagat32.authservice.dto.LoginRequestDTO;
import com.rbhagat32.authservice.dto.RegisterRequestDTO;
import com.rbhagat32.authservice.dto.UserDTO;
import com.rbhagat32.authservice.entity.UserEntity;
import com.rbhagat32.authservice.enums.RoleEnum;
import com.rbhagat32.authservice.repository.UserRepository;
import com.rbhagat32.authservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ModelMapper modelMapper;

    public AuthResponseDTO register(RegisterRequestDTO body) {
        if (userRepository.findByEmail(body.getEmail()).isPresent()) {
            throw new BadCredentialsException("Email is already registered");
        }

        Set<RoleEnum> roles = new HashSet<>();
        roles.add(RoleEnum.ROLE_USER);

        UserEntity user = new UserEntity();
        user.setName(body.getName());
        user.setEmail(body.getEmail());
        user.setPassword(passwordEncoder.encode(body.getPassword()));
        user.setRoles(roles);

        UserEntity savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser);

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

    public UserDTO getLoggedInUser() {
        UserEntity loggedInUser = ((UserEntity) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal()
        );

        return modelMapper.map(loggedInUser, UserDTO.class);
    }
}