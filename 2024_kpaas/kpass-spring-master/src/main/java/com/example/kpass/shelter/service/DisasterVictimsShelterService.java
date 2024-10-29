package com.example.kpass.shelter.service;

import com.example.kpass.shelter.entity.DisasterVictimsShelter;
import com.example.kpass.shelter.repository.DisasterVictimsShelterRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisasterVictimsShelterService {

    @Autowired
    private DisasterVictimsShelterRepository disasterVictimsShelterRepository;

    public List<DisasterVictimsShelter> getTopSheltersByCapacity(double swLat, double neLat, double swLng, double neLng, int zoomLevel) {
        Pageable top30 = PageRequest.of(0, 30);

        if (zoomLevel < 7) {
            // 전국 단위로 상위 30개의 대피소만 반환
            return disasterVictimsShelterRepository.findAll(PageRequest.of(0, 30, Sort.by(Sort.Direction.DESC, "capacity"))).getContent();
        } else {
            // 해당 좌표 내 상위 30개의 대피소만 반환
            return disasterVictimsShelterRepository.findTopSheltersByCapacity(swLat, neLat, swLng, neLng, top30);
        }
    }
}

