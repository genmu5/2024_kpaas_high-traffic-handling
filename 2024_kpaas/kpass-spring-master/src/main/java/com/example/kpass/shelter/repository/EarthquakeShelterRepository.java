package com.example.kpass.shelter.repository;

import com.example.kpass.shelter.entity.EarthquakeShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EarthquakeShelterRepository extends JpaRepository<EarthquakeShelter, Long> {

    @Query(value = "SELECT * FROM earthquake_shelters WHERE lat BETWEEN :swLat AND :neLat AND lng BETWEEN :swLng AND :neLng ORDER BY capacity DESC LIMIT 30", nativeQuery = true)
    List<EarthquakeShelter> findTop30WithinBounds(@Param("swLat") double swLat, @Param("swLng") double swLng, @Param("neLat") double neLat, @Param("neLng") double neLng);
}