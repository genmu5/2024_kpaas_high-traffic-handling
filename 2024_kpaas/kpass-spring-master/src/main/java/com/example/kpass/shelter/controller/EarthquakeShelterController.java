package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.EarthquakeShelter;
import com.example.kpass.shelter.service.EarthquakeShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class EarthquakeShelterController {

    @Autowired
    private EarthquakeShelterService earthquakeShelterService;

    @GetMapping("/earthquake")
    public List<EarthquakeShelter> getTopEarthquakeShelters(
            @RequestParam double swLat,
            @RequestParam double neLat,
            @RequestParam double swLng,
            @RequestParam double neLng,
            @RequestParam int zoom) {
        return earthquakeShelterService.getTopSheltersByCapacity(swLat, neLat, swLng, neLng, zoom);
    }
}
