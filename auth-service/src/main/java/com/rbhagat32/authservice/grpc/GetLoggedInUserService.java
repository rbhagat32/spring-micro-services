package com.rbhagat32.authservice.grpc;

import GetLoggedInUser.GetLoggedInUserRequest;
import GetLoggedInUser.GetLoggedInUserResponse;
import GetLoggedInUser.GetLoggedInUserServiceGrpc.GetLoggedInUserServiceImplBase;
import com.rbhagat32.authservice.entity.UserEntity;
import com.rbhagat32.authservice.repository.UserRepository;
import com.rbhagat32.authservice.security.JwtUtil;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class GetLoggedInUserService extends GetLoggedInUserServiceImplBase {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void getLoggedInUser(GetLoggedInUserRequest request, StreamObserver<GetLoggedInUserResponse> responseObserver) {
        try {
            String token = request.getTOKEN();

            // validate TOKEN is present or not in request
            if (token == null || token.isBlank()) {
                responseObserver.onError(Status.UNAUTHENTICATED
                        .withDescription("Missing TOKEN in request")
                        .asRuntimeException());
                return;
            }

            // extract userId from TOKEN
            String userId;
            try {
                userId = jwtUtil.extractUserId(token);
            } catch (Exception ex) {
                responseObserver.onError(Status.UNAUTHENTICATED
                        .withDescription("Invalid or expired TOKEN")
                        .withCause(ex)
                        .asRuntimeException());
                return;
            }

            // find user
            UserEntity loggedInUser = userRepository.findById(userId).orElse(null);
            if (loggedInUser == null) {
                responseObserver.onError(Status.NOT_FOUND
                        .withDescription("User not found for ID: " + userId)
                        .asRuntimeException());
                return;
            }

            GetLoggedInUserResponse response = GetLoggedInUserResponse.newBuilder()
                    .setId(loggedInUser.getId())
                    .setName(loggedInUser.getName())
                    .setEmail(loggedInUser.getEmail())
                    .setPassword(loggedInUser.getPassword())
                    .setCreatedAt(loggedInUser.getCreatedAt().toString())
                    .setUpdatedAt(loggedInUser.getUpdatedAt().toString())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL
                    .withDescription("Error fetching logged in user: " + e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }
}