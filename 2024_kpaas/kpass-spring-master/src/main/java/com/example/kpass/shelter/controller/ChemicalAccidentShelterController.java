package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.ChemicalAccidentShelter;
import com.example.kpass.shelter.service.ChemicalAccidentShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class ChemicalAccidentShelterController {

    @Autowired
    private ChemicalAccidentShelterService chemicalAccidentShelterService;

//    // 화학 사고 대피소 데이터를 조회
//    @GetMapping("/chemical")
//    public List<ChemicalAccidentShelter> getChemicalShelters() {
//        return chemicalAccidentShelterService.getAllChemicalShelters();
//    }
    //상위 30개의 대피소 정보 가져오기
    @GetMapping("/chemical")
    public List<ChemicalAccidentShelter> getChemicalShelters(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng) {
        System.out.println("swLat: " + swLat + ", swLng: " + swLng + ", neLat: " + neLat + ", neLng: " + neLng);
        return chemicalAccidentShelterService.getTop30WithinBounds(swLat, swLng, neLat, neLng);
    }
}
