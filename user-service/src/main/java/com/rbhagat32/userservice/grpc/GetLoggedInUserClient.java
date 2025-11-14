package com.rbhagat32.userservice.grpc;

import GetLoggedInUser.GetLoggedInUserRequest;
import GetLoggedInUser.GetLoggedInUserResponse;
import GetLoggedInUser.GetLoggedInUserServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GetLoggedInUserClient {

    private final GetLoggedInUserServiceGrpc.GetLoggedInUserServiceBlockingStub blockingStub;

    public GetLoggedInUserClient(
            @Value("${auth.service.host}") String serverHost,
            @Value("${auth.service.grpc.port}") int serverPort
    ) {
        System.out.println("Connecting to Auth-Service through GRPC at " + serverHost + ":" + serverPort);
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverHost, serverPort).usePlaintext().build();
        blockingStub = GetLoggedInUserServiceGrpc.newBlockingStub(channel);
    }

    public GetLoggedInUserResponse getLoggedInUser(String TOKEN) {
        GetLoggedInUserRequest request = GetLoggedInUserRequest.newBuilder().setTOKEN(TOKEN).build();

        GetLoggedInUserResponse response = blockingStub.getLoggedInUser(request);
        return response;
    }
}