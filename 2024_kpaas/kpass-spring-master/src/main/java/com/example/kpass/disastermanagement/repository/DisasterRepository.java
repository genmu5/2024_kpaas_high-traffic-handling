package com.example.kpass.disastermanagement.repository;

import com.example.kpass.disastermanagement.entity.DisasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface DisasterRepository extends JpaRepository<DisasterEntity, Long> {

    // 특정 시간 범위의 데이터를 조회하는 메서드
    List<DisasterEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    // 동일한 재난 데이터가 이미 존재하는지 여부를 확인하는 메서드
    boolean existsByCreatedAtAndRegionNameAndDisasterType(LocalDateTime createdAt, String regionName, String disasterType);
}
