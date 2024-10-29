package com.example.kpass.shelter.repository;

import com.example.kpass.shelter.entity.ChemicalAccidentShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChemicalAccidentShelterRepository extends JpaRepository<ChemicalAccidentShelter, Long> {

    @Query(value = "SELECT * FROM chemical_accident_shelter WHERE lat BETWEEN :swLat AND :neLat AND lng BETWEEN :swLng AND :neLng ORDER BY capacity DESC LIMIT 30", nativeQuery = true)
    List<ChemicalAccidentShelter> findTop30WithinBounds(@Param("swLat") double swLat, @Param("swLng") double swLng, @Param("neLat") double neLat, @Param("neLng") double neLng);
}

