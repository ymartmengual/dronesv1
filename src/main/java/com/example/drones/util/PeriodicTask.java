package com.example.drones.util;

import com.example.drones.repository.IDroneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.logging.Level;
import java.util.logging.Logger;


//«Segundos» «Minutos» «Horas» «Día del mes» «Mes» «Día de la semana» «Año»
//* : Selecciona todos los valores de un campo (por ejemplo cada hora, cada minuto)
//0 15 * * * ?	Se ejecuta cada 15 minutos
//0 0 15 * * ?	Se ejecuta cada día a las 15:00:00
//0 15,25 15 * * ?	Se ejecuta a las 15:15:00 y a las 15:25:00

@Component
public class PeriodicTask {

    @Autowired
    IDroneRepository droneRepository;

    @Scheduled(fixedRate = 10000)
    public void checkDronesBatteryLevel() {
        try {
            droneRepository.findAll().forEach(drone -> {
                Logger.getLogger(PeriodicTask.class.getName()).log(Level.INFO, drone.getBattery().toString());
            });
        }catch (Exception e){
            Logger.getLogger(PeriodicTask.class.getName()).log(Level.WARNING, e.getMessage());
        }
    }
}
