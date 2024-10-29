package com.example.kpass.shelter.service;

import com.example.kpass.shelter.entity.LandslideShelter;
import com.example.kpass.shelter.repository.LandslideShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//public class LandslideShelterService {
//
//    @Autowired
//    private LandslideShelterRepository landslideShelterRepository;
//
//    //모든 산사태 대피소들
//    public List<LandslideShelter> getAllLandslideShelters() {
//        return landslideShelterRepository.findAll();
//    }
//}
@Service
public class LandslideShelterService {

    @Autowired
    private LandslideShelterRepository landslideShelterRepository;

    public List<LandslideShelter> getTop30WithinBounds(double swLat, double swLng, double neLat, double neLng) {
        return landslideShelterRepository.findTop30WithinBounds(swLat, swLng, neLat, neLng);
    }
}