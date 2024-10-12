package com.example.kpass.newsManagement.repository;

import com.example.kpass.newsManagement.entity.NaverNews;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NaverNewsRepository extends JpaRepository<NaverNews, Integer> {
    boolean existsByLink(String link); // 중복 확인
    List<NaverNews> findTop10ByOrderByPubDateDesc(); // 최신 뉴스 10개 가져오기
}
