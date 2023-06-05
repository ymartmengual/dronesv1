package com.example.drones.service.impl;

import com.example.drones.repository.IMedicationRepository;
import com.example.drones.service.IMedicationService;
import org.springframework.beans.factory.annotation.Autowired;

public class MedicationServiceImpl implements IMedicationService {

    @Autowired
    IMedicationRepository medicationRepository;

    @Override
    public boolean existMedicationByCodeAndName(String name, String code) {
        return medicationRepository.findAll().stream().anyMatch(medication -> medication.getName().equalsIgnoreCase(name) && medication.getCode().equalsIgnoreCase(code));
    }
}
