package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.CivilDefenseShelter;
import com.example.kpass.shelter.service.CivilDefenseShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class CivilDefenseShelterController {

    @Autowired
    private CivilDefenseShelterService civilDefenseShelterService;

//    @GetMapping("/civil-defense")
//    public List<CivilDefenseShelter> getCivilDefenseShelters() {
//        return civilDefenseShelterService.getAllCivilDefenseShelters();
//    }
    @GetMapping("/civil-defense")
    public List<CivilDefenseShelter> getCivilDefenseShelters(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng) {
        return civilDefenseShelterService.getTop30WithinBounds(swLat, swLng, neLat, neLng);
    }

}
