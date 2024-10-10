package com.example.kpass.disastermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisasterResponseDto {
    private String createdAt;    // 생성일시 (CRT_DT)
    private String disasterType; // 재해구분명 (DST_SE_NM)
    private String regionName;   // 수신지역명 (RCPTN_RGN_NM)
    private Double latitude;     // 위도
    private Double longitude;    // 경도
}
