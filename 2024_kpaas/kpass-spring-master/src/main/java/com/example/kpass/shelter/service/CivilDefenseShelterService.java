package com.example.kpass.shelter.service;

import com.example.kpass.shelter.entity.CivilDefenseShelter;
import com.example.kpass.shelter.repository.CivilDefenseShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class CivilDefenseShelterService {
//
//    @Autowired
//    private CivilDefenseShelterRepository civilDefenseShelterRepository;
//
//    //모든 민방위 대피소 데이터들
//    public List<CivilDefenseShelter> getAllCivilDefenseShelters() {
//        return civilDefenseShelterRepository.findAll();
//    }
//}
@Service
public class CivilDefenseShelterService {

    @Autowired
    private CivilDefenseShelterRepository civilDefenseShelterRepository;

    public List<CivilDefenseShelter> getTop30WithinBounds(double swLat, double swLng, double neLat, double neLng) {
        return civilDefenseShelterRepository.findTop30WithinBounds(swLat, swLng, neLat, neLng);
    }
}

