package com.example.drones.repository;

import com.example.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMedicationRepository  extends JpaRepository<Medication, Long> {
}
