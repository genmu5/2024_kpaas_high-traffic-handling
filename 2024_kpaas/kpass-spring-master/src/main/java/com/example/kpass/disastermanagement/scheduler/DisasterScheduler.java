package com.example.kpass.disastermanagement.scheduler;


import com.example.kpass.disastermanagement.controller.DisasterController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; ////
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class DisasterScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DisasterScheduler.class);

    private final DisasterController disasterController;

    public DisasterScheduler(DisasterController disasterController) {
        this.disasterController = disasterController;
    }

    // 1.5분(90초)마다 getDisasters 메서드 호출
    @Scheduled(fixedRate = 90000)
    public void scheduleDisasterFetch() {
        logger.info("스케줄러가 API를 호출합니다.");
        disasterController.getDisasters();
        logger.info("스케줄러가 API 호출을 완료했습니다.");
    }
}
