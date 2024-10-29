package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.LandslideShelter;
import com.example.kpass.shelter.service.LandslideShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/shelters")
public class LandslideShelterController {

    @Autowired
    private LandslideShelterService landslideShelterService;

//    @GetMapping("/landslide")
//    public List<LandslideShelter> getLandslideShelters() {
//        return landslideShelterService.getAllLandslideShelters();
//    }
    @GetMapping("/landslide")
    public List<LandslideShelter> getLandslideShelters(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng) {
        return landslideShelterService.getTop30WithinBounds(swLat, swLng, neLat, neLng);
    }
}
