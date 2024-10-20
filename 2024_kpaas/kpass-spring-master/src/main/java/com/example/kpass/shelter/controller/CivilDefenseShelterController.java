package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.CivilDefenseShelter;
import com.example.kpass.shelter.service.CivilDefenseShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class CivilDefenseShelterController {

    @Autowired
    private CivilDefenseShelterService civilDefenseShelterService;

    @GetMapping("/civil-defense")
    public List<CivilDefenseShelter> getTopCivilDefenseShelters(
            @RequestParam double swLat,
            @RequestParam double neLat,
            @RequestParam double swLng,
            @RequestParam double neLng,
            @RequestParam int zoom) {
        return civilDefenseShelterService.getTopSheltersByCapacity(swLat, neLat, swLng, neLng, zoom);
    }
}
