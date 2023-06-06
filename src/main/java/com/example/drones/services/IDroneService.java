package com.example.drones.services;

import com.example.drones.entity.Drone;

import java.util.List;
import java.util.Optional;

public interface IDroneService {

    boolean existDroneBySerialNumber(String serialNumber);

    Drone save(Drone drone);

    Optional<Drone> findById(Long idDrone);

    List<Drone> findAll();

    List<Drone> findAllAvailable();

}
