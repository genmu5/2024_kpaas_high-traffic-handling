package com.example.kpass.newsManagement.controller;

import com.example.kpass.newsManagement.entity.NaverNews;
import com.example.kpass.newsManagement.service.NaverNewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/navernews")
public class NaverNewsController {
    private final NaverNewsService naverNewsService;

    public NaverNewsController(NaverNewsService naverNewsService) {
        this.naverNewsService = naverNewsService;
    }

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndStoreNews() {
        try {
            naverNewsService.fetchAndStoreNews();
            return new ResponseEntity<>("뉴스 데이터가 성공적으로 저장되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("뉴스 데이터를 가져오는 중 오류가 발생했습니다: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/latest")
    public List<NaverNews> getLatestNews() {
        return naverNewsService.getLatestNews();
    }
}
