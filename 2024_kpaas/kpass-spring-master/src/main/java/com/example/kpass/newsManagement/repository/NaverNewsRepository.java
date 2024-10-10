package com.example.kpass.newsManagement.repository;

import com.example.kpass.newsManagement.entity.NaverNews;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaverNewsRepository extends JpaRepository<NaverNews, Integer> {
}
