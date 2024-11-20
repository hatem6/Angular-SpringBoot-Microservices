package com.example.offer_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Map requests to /uploads/** to the actual directory on disk
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:backend/offer-service/src/main/uploads/");
    }
}
