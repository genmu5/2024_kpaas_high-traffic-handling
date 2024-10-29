package com.example.kpass.shelter.service;

import com.example.kpass.shelter.entity.ChemicalAccidentShelter;
import com.example.kpass.shelter.repository.ChemicalAccidentShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChemicalAccidentShelterService {

    @Autowired
    private ChemicalAccidentShelterRepository chemicalAccidentShelterRepository;

    // 모든 화학 사고 대피소 데이터들
    public List<ChemicalAccidentShelter> getAllChemicalShelters() {
        return chemicalAccidentShelterRepository.findAll();
    }
}
