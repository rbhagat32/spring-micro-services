package com.rbhagat32.apigateway.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.time.Instant;

@Component
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;

    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder, @Value("${auth.service.url}") String authServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {

            // try AUTHORIZATION header
            String authHeader = exchange.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            // try TOKEN cookie
            HttpCookie cookie = exchange.getRequest()
                    .getCookies()
                    .getFirst("TOKEN");

            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
            } else if (cookie != null) {
                token = cookie.getValue();
            }

            // if no token found → UNAUTHORIZED
            if (token == null) {
                return ApiError(exchange, "Unauthorized: Token not found");
            }

            return webClient.get()
                    .uri("/auth/validate-token")
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .retrieve()
                    .toBodilessEntity()
                    .flatMap(res -> chain.filter(exchange))
                    .onErrorResume(ex -> ApiError(exchange, "Unauthorized: Invalid or expired token"));
        };
    }

    private Mono<Void> ApiError(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        String path = exchange.getRequest().getURI().getPath();

        String body = """
                {
                    "timeStamp": "%s"
                    "statusCode": "%s",
                    "error": "%s",
                    "message": "%s",
                    "path": "%s"
                }
                """.formatted(Instant.now(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase(),
                "Unauthorized",
                message,
                path);

        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = response.bufferFactory().wrap(bytes);

        return response.writeWith(Mono.just(buffer));
    }
}