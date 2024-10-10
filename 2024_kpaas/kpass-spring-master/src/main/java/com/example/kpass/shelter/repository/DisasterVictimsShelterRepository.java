package com.example.kpass.shelter.repository;

import com.example.kpass.shelter.entity.DisasterVictimsShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisasterVictimsShelterRepository extends JpaRepository<DisasterVictimsShelter, Long> {
}
