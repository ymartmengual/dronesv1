package com.example.drones.services.impl;

import com.example.drones.entity.Drone;
import com.example.drones.enums.DroneStatus;
import com.example.drones.repository.IDroneRepository;
import com.example.drones.services.IDroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DroneServiceImpl implements IDroneService {

    @Autowired
    private IDroneRepository droneRepository;

    @Override
    public boolean existDroneBySerialNumber(String serialNumber) {
        return droneRepository.findAll().stream().anyMatch(dron-> dron.getSerialNumber().equalsIgnoreCase(serialNumber));
    }

    @Override
    public Drone save(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public Optional<Drone> findById(Long idDrone) {
        return droneRepository.findById(idDrone);
    }

    @Override
    public List<Drone> findAll() {
        return droneRepository.findAll();
    }

    @Override
    public List<Drone> findAllAvailable() {
        return droneRepository.findAll().stream().filter(drone-> drone.getDroneStatus().equals(DroneStatus.IDLE)).collect(Collectors.toList());
    }
}
