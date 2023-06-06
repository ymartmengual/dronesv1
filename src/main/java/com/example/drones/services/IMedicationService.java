package com.example.drones.services;

import com.example.drones.entity.Medication;

public interface IMedicationService {

    boolean existMedicationByCodeAndName(String name, String code);

    Medication save(Medication medication);

    Medication findById(Long idMedication);
}
