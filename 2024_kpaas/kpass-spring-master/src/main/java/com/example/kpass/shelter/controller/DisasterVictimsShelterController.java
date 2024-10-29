package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.DisasterVictimsShelter;
import com.example.kpass.shelter.service.DisasterVictimsShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class DisasterVictimsShelterController {

    @Autowired
    private DisasterVictimsShelterService disasterVictimsShelterService;

//    @GetMapping("/disaster-victims")
//    public List<DisasterVictimsShelter> getDisasterVictimsShelters() {
//        return disasterVictimsShelterService.getAllDisasterVictimsShelters();
//    }
    @GetMapping("/disaster-victims")
    public List<DisasterVictimsShelter> getDisasterVictimsShelters(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng) {
        return disasterVictimsShelterService.getTop30WithinBounds(swLat, swLng, neLat, neLng);
    }
}
