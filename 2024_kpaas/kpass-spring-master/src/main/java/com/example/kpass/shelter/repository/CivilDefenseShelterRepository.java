package com.example.kpass.shelter.repository;

import com.example.kpass.shelter.entity.CivilDefenseShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CivilDefenseShelterRepository extends JpaRepository<CivilDefenseShelter, Long> {
}
