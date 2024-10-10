package com.example.kpass.shelter.controller;

import com.example.kpass.shelter.entity.EarthquakeShelter;
import com.example.kpass.shelter.service.EarthquakeShelterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shelters")
public class EarthquakeShelterController {

    @Autowired
    private EarthquakeShelterService earthquakeShelterService;

    @GetMapping("/earthquake")
    public List<EarthquakeShelter> getEarthquakeShelters() {
        return earthquakeShelterService.getAllEarthquakeShelters();
    }
}
