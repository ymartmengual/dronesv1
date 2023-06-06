package com.example.drones.util;

import com.example.drones.entity.Audit;
import com.example.drones.repository.IDroneRepository;
import com.example.drones.services.IAuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class PeriodicTask {

    @Autowired
    private IDroneRepository droneRepository;

    @Autowired
    private IAuditService auditService;

    @Scheduled(fixedRate = 10000)
    public void checkDronesBatteryLevel() {
        try {
            droneRepository.findAll().forEach(drone -> {
                Audit audit = new Audit(null, LocalDateTime.now(), drone.getSerialNumber(), drone.getBattery());
                auditService.save(audit);
                Logger.getLogger(PeriodicTask.class.getName()).log(Level.INFO, drone.getBattery().toString());
            });
        }catch (Exception e){
            Logger.getLogger(PeriodicTask.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }
}
