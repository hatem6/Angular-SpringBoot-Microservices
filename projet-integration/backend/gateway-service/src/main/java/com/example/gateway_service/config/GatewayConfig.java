package com.example.gateway_service.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("agency-service", r -> r.path("/api/agences/**")
                    .uri("http://localhost:8081/"))
            .route("offer-service", r -> r.path("/api/offers/**")
                    .uri("http://localhost:8082/"))
            .route("user-service", r -> r.path("/api/users/**")
                    .uri("http://localhost:8083/"))
            .build();
            
    }
}

