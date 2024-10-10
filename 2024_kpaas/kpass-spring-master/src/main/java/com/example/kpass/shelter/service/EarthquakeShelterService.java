package com.example.kpass.shelter.service;

import com.example.kpass.shelter.entity.EarthquakeShelter;
import com.example.kpass.shelter.repository.EarthquakeShelterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EarthquakeShelterService {

    @Autowired
    private EarthquakeShelterRepository earthquakeShelterRepository;

    //모든 지진 옥외 대피장소 데이터들
    public List<EarthquakeShelter> getAllEarthquakeShelters() {
        return earthquakeShelterRepository.findAll();
    }
}
