package com.example.drones.service;

import com.example.drones.entity.Drone;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface IDroneService {

    boolean existDroneBySerialNumber(String serialNumber);

    Drone save(Drone drone);

    Optional<Drone> findById(Long idDrone);

    List<Drone> findAll();

    List<Drone> findAllAvailable();

}
