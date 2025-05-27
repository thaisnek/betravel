package com.example.travelweb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/avatar/**")
                .addResourceLocations("file:C:/Users/Dinh Thai/Documents/images/avatar/");
        registry.addResourceHandler("/images/tour/**")
                .addResourceLocations("file:C:/Users/Dinh Thai/Documents/images/tour/");
    }

}