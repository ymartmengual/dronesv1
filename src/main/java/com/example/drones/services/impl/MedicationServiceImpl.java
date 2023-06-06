package com.example.drones.services.impl;

import com.example.drones.entity.Medication;
import com.example.drones.repository.IMedicationRepository;
import com.example.drones.services.IMedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MedicationServiceImpl implements IMedicationService {

    @Autowired
    IMedicationRepository medicationRepository;

    @Override
    public boolean existMedicationByCodeAndName(String name, String code) {
        return medicationRepository.findAll().stream().anyMatch(medication -> medication.getName().equalsIgnoreCase(name) && medication.getCode().equalsIgnoreCase(code));
    }

    @Override
    public Medication save(Medication medication) {
        medication.setIdMedication(null);
        return medicationRepository.save(medication);
    }

    @Override
    public Medication findById(Long idMedication) {
        Optional<Medication> medication = medicationRepository.findById(idMedication);
        if(medication.isPresent())
            return medication.get();
        return null;
    }
}
