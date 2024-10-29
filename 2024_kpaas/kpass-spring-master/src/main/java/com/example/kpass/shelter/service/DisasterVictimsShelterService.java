package com.example.kpass.shelter.service;

import com.example.kpass.shelter.entity.DisasterVictimsShelter;
import com.example.kpass.shelter.repository.DisasterVictimsShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class DisasterVictimsShelterService {
//
//    @Autowired
//    private DisasterVictimsShelterRepository disasterVictimsShelterRepository;
//
//    //모든 산사태 대피소 데이터들
//    public List<DisasterVictimsShelter> getAllDisasterVictimsShelters() {
//        return disasterVictimsShelterRepository.findAll();
//    }
//}
@Service
public class DisasterVictimsShelterService {

    @Autowired
    private DisasterVictimsShelterRepository disasterVictimsShelterRepository;

    public List<DisasterVictimsShelter> getTop30WithinBounds(double swLat, double swLng, double neLat, double neLng) {
        return disasterVictimsShelterRepository.findTop30WithinBounds(swLat, swLng, neLat, neLng);
    }
}

