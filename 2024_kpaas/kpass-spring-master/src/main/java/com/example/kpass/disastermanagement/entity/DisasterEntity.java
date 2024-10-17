package com.example.kpass.disastermanagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor // 기본 생성자 생성
@AllArgsConstructor // 모든 필드 값을 인자로 받는 생성자 생성
public class DisasterEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 자동 생성 ID

    private LocalDateTime createdAt; // 생성일시 (CRT_DT)
    private String regionName; // 수신지역명 (RCPTN_RGN_NM)
    private String disasterType; // 재해구분명 (DST_SE_NM)

    // Lombok이 자동으로 getter/setter 및 생성자 메서드를 생성합니다.
}
