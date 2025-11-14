package com.rbhagat32.userservice.service;

import GetLoggedInUser.GetLoggedInUserResponse;
import com.rbhagat32.userservice.dto.UserDTO;
import com.rbhagat32.userservice.grpc.GetLoggedInUserClient;
import com.rbhagat32.userservice.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final TokenUtil tokenUtil;
    private final GetLoggedInUserClient getLoggedInUserClient;
    private final ModelMapper modelMapper;

    public UserDTO getLoggedInUser(HttpServletRequest request) {
        String token = tokenUtil.extractToken(request);
        if (token == null) throw new RuntimeException("Token not found in request");
        GetLoggedInUserResponse loggedInUser = getLoggedInUserClient.getLoggedInUser(token);

        return modelMapper.map(loggedInUser, UserDTO.class);
    }
}