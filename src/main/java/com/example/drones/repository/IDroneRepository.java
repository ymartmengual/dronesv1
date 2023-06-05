package com.example.drones.repository;

import com.example.drones.entity.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDroneRepository extends JpaRepository<Drone, Long> {
}
