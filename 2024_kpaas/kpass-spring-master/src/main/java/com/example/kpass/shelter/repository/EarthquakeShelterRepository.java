package com.example.kpass.shelter.repository;

import com.example.kpass.shelter.entity.EarthquakeShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarthquakeShelterRepository extends JpaRepository<EarthquakeShelter, Long> {
}
