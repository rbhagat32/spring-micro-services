package com.rbhagat32.userservice.controller;

import GetLoggedInUser.GetLoggedInUserResponse;
import com.rbhagat32.userservice.dto.UserDTO;
import com.rbhagat32.userservice.grpc.GetLoggedInUserClient;
import com.rbhagat32.userservice.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final HttpServletRequest request;
    private final TokenUtil tokenUtil;
    private final GetLoggedInUserClient getLoggedInUserClient;
    private final ModelMapper modelMapper;

    @GetMapping("/hello")
    public UserDTO hello() {
        String token = tokenUtil.extractToken(request);
        if (token == null) throw new RuntimeException("Token not found in request");
        GetLoggedInUserResponse loggedInUser = getLoggedInUserClient.getLoggedInUser(token);

        return modelMapper.map(loggedInUser, UserDTO.class);
    }
}