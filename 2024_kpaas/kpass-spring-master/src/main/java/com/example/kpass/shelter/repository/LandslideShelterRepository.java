package com.example.kpass.shelter.repository;



import com.example.kpass.shelter.entity.LandslideShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandslideShelterRepository extends JpaRepository<LandslideShelter, Long> {
}
