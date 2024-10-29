package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.EarthquakeShelter;
import com.example.kpass.shelter.service.EarthquakeShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/shelters")
public class EarthquakeShelterController {

    @Autowired
    private EarthquakeShelterService earthquakeShelterService;

//    @GetMapping("/earthquake")
//    public List<EarthquakeShelter> getEarthquakeShelters() {
//        return earthquakeShelterService.getAllEarthquakeShelters();
//    }
    @GetMapping("/earthquake")
    public List<EarthquakeShelter> getEarthquakeShelters(
            @RequestParam double swLat,
            @RequestParam double swLng,
            @RequestParam double neLat,
            @RequestParam double neLng) {
        return earthquakeShelterService.getTop30WithinBounds(swLat, swLng, neLat, neLng);
    }
}
