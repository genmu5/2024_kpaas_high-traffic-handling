package com.example.kpass;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 스케줄링 기능 활성화
@EnableJpaAuditing
public class KpassApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpassApplication.class, args);
    }

}
