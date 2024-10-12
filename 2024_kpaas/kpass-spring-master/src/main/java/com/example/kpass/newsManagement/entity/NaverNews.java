package com.example.kpass.newsManagement.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "naver_news")
@AllArgsConstructor
@NoArgsConstructor
public class NaverNews {
    @Id
    @Column(name = "naver_news_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String link;
    private LocalDateTime pubDate;

}
