package com.ohgiraffers.crud_back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:8081", "https://connectium-frontend.vercel.app", "http://1.214.19.22:8081",
                                        "https://yeyak.seoul.go.kr", "http://192.168.0.40", "http://10.0.2.2:8080")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
                        // 응답 헤더 허용 : 서버에서 특정 헤더를 클라이언트로 노출
                        .allowCredentials(true)
                        .maxAge(3600); // 브라우저의 PreFlight 요청을 캐시하여 성능을 향상
            }
        };
    }
}