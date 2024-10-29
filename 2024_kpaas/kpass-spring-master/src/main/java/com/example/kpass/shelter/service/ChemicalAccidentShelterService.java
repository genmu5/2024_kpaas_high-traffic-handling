package com.example.kpass.shelter.service;

import com.example.kpass.shelter.entity.ChemicalAccidentShelter;
import com.example.kpass.shelter.repository.ChemicalAccidentShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChemicalAccidentShelterService {

//    @Autowired
//    private ChemicalAccidentShelterRepository chemicalAccidentShelterRepository;
//
//    // 모든 화학 사고 대피소 데이터들
//    public List<ChemicalAccidentShelter> getAllChemicalShelters() {
//        return chemicalAccidentShelterRepository.findAll();
//    }

    //상위 30개의 대피소 데이터들 가져오기
    @Autowired
    private ChemicalAccidentShelterRepository chemicalAccidentShelterRepository;

    public List<ChemicalAccidentShelter> getTop30WithinBounds(double swLat, double swLng, double neLat, double neLng) {
        return chemicalAccidentShelterRepository.findTop30WithinBounds(swLat, swLng, neLat, neLng);
    }
}
