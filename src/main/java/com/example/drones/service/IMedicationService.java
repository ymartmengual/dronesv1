package com.example.drones.service;


import org.springframework.stereotype.Service;

@Service
public interface IMedicationService {

    boolean existMedicationByCodeAndName(String name, String code);
}
