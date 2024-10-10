package com.example.kpass.shelter.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "chemical_accident_shelter")
public class ChemicalAccidentShelter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chemicalAccidentShelterId;
    private String name;
    private String address;
    private Long capacity;
    private Double lat;
    private Double lng;
}
