package com.example.kpass.disastermanagement.service;

import com.example.kpass.disastermanagement.entity.DisasterEntity;
import com.example.kpass.disastermanagement.repository.DisasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor // final 필드에 대한 생성자 자동 생성
public class DisasterService {

    private final DisasterRepository disasterRepository;

    // 재난 유형 리스트
    private final List<String> disasterTypes = Arrays.asList(
            "산사태", "조수", "지진", "폭염", "풍수해", "감염병",
            "다중밀집건축물붕괴대형사고", "산불", "초미세먼지", "해양선박사고", "한파", "화재", "호우"
    );

    // 재난 정보를 필터링하고 DB에 저장하는 메서드
    public void filterAndSaveDisaster(String createdAt, String regionName, String disasterType) {
        // 재난 유형 필터링
        if (disasterTypes.contains(disasterType)) {
            // createdAt 문자열을 LocalDateTime으로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime createdDateTime = LocalDateTime.parse(createdAt, formatter);

            // 중복 확인: 같은 createdAt, regionName, disasterType이 이미 있는지 검사
            boolean exists = disasterRepository.existsByCreatedAtAndRegionNameAndDisasterType(createdDateTime, regionName, disasterType);

            if (!exists) {
                // 중복되지 않는 경우에만 DB에 저장
                DisasterEntity disasterEntity = new DisasterEntity();
                disasterEntity.setCreatedAt(createdDateTime);  // 변환된 LocalDateTime 사용
                disasterEntity.setRegionName(regionName);
                disasterEntity.setDisasterType(disasterType);

                disasterRepository.save(disasterEntity); // DB에 저장
                System.out.println("새로운 재난 정보가 저장되었습니다: " + disasterEntity);
            } else {
                System.out.println("이미 존재하는 재난 정보입니다: " + disasterType + " (" + regionName + ", " + createdAt + ")");
            }
        } else {
            // 재난 유형이 아닐 경우 저장하지 않음
            System.out.println("기타로 분류된 재난: " + disasterType);
        }
    }
}
