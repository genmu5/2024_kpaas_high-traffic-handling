package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.ChemicalAccidentShelter;
import com.example.kpass.shelter.service.ChemicalAccidentShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class ChemicalAccidentShelterController {

    @Autowired
    private ChemicalAccidentShelterService chemicalAccidentShelterService;

    // 화학 사고 대피소 데이터를 조회
    @GetMapping("/chemical")
    public List<ChemicalAccidentShelter> getChemicalShelters() {
        return chemicalAccidentShelterService.getAllChemicalShelters();
    }
}
