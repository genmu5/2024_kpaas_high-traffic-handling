package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.DisasterVictimsShelter;
import com.example.kpass.shelter.service.DisasterVictimsShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class DisasterVictimsShelterController {

    @Autowired
    private DisasterVictimsShelterService disasterVictimsShelterService;

    @GetMapping("/disaster-victims")
    public List<DisasterVictimsShelter> getTopDisasterVictimsShelters(
            @RequestParam double swLat,
            @RequestParam double neLat,
            @RequestParam double swLng,
            @RequestParam double neLng,
            @RequestParam int zoom) {
        return disasterVictimsShelterService.getTopSheltersByCapacity(swLat, neLat, swLng, neLng, zoom);
    }
}
