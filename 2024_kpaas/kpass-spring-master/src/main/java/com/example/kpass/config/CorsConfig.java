package com.example.kpass.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    // ConfigMap에서 읽어온 도메인 리스트를 가져오기
    @Value("${spring.cors.allowed-origins}")
    private String allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // ConfigMap에서 가져온 도메인 값을 쉼표(,)로 구분하여 배열로 변환
        String[] originsArray = allowedOrigins.split(",");

        registry.addMapping("/**")
                .allowedOrigins(originsArray)  // ConfigMap에서 가져온 동적 도메인 사용
                .allowedOrigins("http://211.188.51.114")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);  // 인증 정보를 포함한 요청 허용
    }

    @PostConstruct
    public void init() {
        System.out.println("Allowed Origins: " + allowedOrigins);
    }
}