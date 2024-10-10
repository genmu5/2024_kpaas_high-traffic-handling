package com.example.kpass.disastermanagement.repository;

import com.example.kpass.disastermanagement.entity.DisasterEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisasterRepository extends JpaRepository<DisasterEntity, Long> {
    // 동일한 재난 데이터가 이미 존재하는지 여부를 확인하는 쿼리 메서드
    boolean existsByCreatedAtAndRegionNameAndDisasterType(String createdAt, String regionName, String disasterType);
}


