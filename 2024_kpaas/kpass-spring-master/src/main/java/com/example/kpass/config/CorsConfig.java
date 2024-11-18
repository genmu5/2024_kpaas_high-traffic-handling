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
        // ConfigMap에서 가져온 도메인 값을 쉼표(,)로 구분하여 배열로 변환하고 추가 도메인 합치기
        String[] originsArray = allowedOrigins.split(",");

        // 고정 도메인을 originsArray에 추가
        String[] combinedOrigins = new String[originsArray.length + 1];
        System.arraycopy(originsArray, 0, combinedOrigins, 0, originsArray.length);
        combinedOrigins[originsArray.length] = "http://211.188.49.195";

        registry.addMapping("/**")
                .allowedOrigins(combinedOrigins)  // 모든 허용된 도메인 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);  // 인증 정보를 포함한 요청 허용
    }

    @PostConstruct
    public void init() {
        System.out.println("Allowed Origins: " + allowedOrigins);
    }
}