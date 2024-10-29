package com.example.kpass.shelter.repository;

import com.example.kpass.shelter.entity.DisasterVictimsShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface DisasterVictimsShelterRepository extends JpaRepository<DisasterVictimsShelter, Long> {
    @Query("SELECT s FROM DisasterVictimsShelter s WHERE s.lat BETWEEN :swLat AND :neLat AND s.lng BETWEEN :swLng AND :neLng ORDER BY s.capacity DESC")
    List<DisasterVictimsShelter> findTopSheltersByCapacity(
            @Param("swLat") double swLat,
            @Param("neLat") double neLat,
            @Param("swLng") double swLng,
            @Param("neLng") double neLng,
            Pageable pageable
    );
}