package com.example.drones.util;

import com.example.drones.repository.IDroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PeriodicTask {

    @Autowired
    IDroneRepository droneRepository;

    @Scheduled(fixedRate = 10000)
    public void checkDronesBatteryLevel() {
        try {
            droneRepository.findAll().forEach(drone -> {
                //todo agregar a una tabla audit la info del logger
                Logger.getLogger(PeriodicTask.class.getName()).log(Level.INFO, drone.getBattery().toString());
            });
        }catch (Exception e){
            Logger.getLogger(PeriodicTask.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }
}
