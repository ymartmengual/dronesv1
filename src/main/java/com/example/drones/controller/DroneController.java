package com.example.drones.controller;

import com.example.drones.constante.Constante;
import com.example.drones.dto.ResultDataResDto;
import com.example.drones.entity.Drone;
import com.example.drones.entity.Medication;
import com.example.drones.enums.DroneStatus;
import com.example.drones.exceptions.GenericErrorException;
import com.example.drones.repository.IDroneRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/drone")
public class DroneController {

    @Autowired
    IDroneRepository droneRepository;

    /**
     * @implNote registering a drone
     * @param drone information structure to create drone with medication items
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Object> save(@Valid @RequestBody Drone drone){
        try{
            double weightFull = 0D;
            for (Medication medicationItem : drone.getMedicationList()) {
                weightFull += medicationItem.getWeight();
            }
            if(drone.getWeightLimit() > 500 || weightFull > drone.getWeightLimit()){
                throw new GenericErrorException("Drone charge limit exceeded",
                        Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
            }
            if(drone.getDroneStatus().equals(DroneStatus.LOADING) && drone.getBattery() < 25){
                throw new GenericErrorException("Drone cannot be in LOADING status if the battery level is below 25%",
                        Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
            }
            return new ResponseEntity<>(droneRepository.save(drone), HttpStatus.CREATED);
        }catch (GenericErrorException e){
            Logger.getLogger(DroneController.class.getName()).log(Level.WARNING, e.getMessage());
            ResultDataResDto resultDataResDto = new ResultDataResDto();
            responseDto(resultDataResDto, e.getCodeError(),
                    e.getMessage(), e.getNameCodeError(), DroneController.class.getName());
            return new ResponseEntity<>(resultDataResDto, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            Logger.getLogger(DroneController.class.getName()).log(Level.SEVERE, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @implNote List all Drones
     * @return
     */
    @GetMapping("/")
    public ResponseEntity<List<Drone>> getAllDrones(){
        try{
            return new ResponseEntity<>(droneRepository.findAll(), HttpStatus.OK);
        }catch (Exception e){
            Logger.getLogger(DroneController.class.getName()).log(Level.SEVERE, e.getMessage());
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @implNote loading a drone with medication items;
     * @param medicationItems
     * @param idDrone
     * @return
     */
    @PostMapping("/{idDrone}/updatemedicationitems")
    public ResponseEntity<Object> loadMedicationItems(@Valid @RequestBody List<Medication> medicationItems, @PathVariable Long idDrone){

        try{
            Optional droneOptional = droneRepository.findById(idDrone);
            if(droneOptional.isPresent()){
                Drone drone = (Drone) droneOptional.get();
                double weightFull = 0D;
                for (Medication medicationItem : medicationItems) {
                    weightFull += medicationItem.getWeight();
                }
                if(weightFull > drone.getWeightLimit()){
                    throw new GenericErrorException("Drone charge limit exceeded",
                            Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
                }
                drone.setMedicationList(medicationItems);

                return new ResponseEntity<>(droneRepository.save(drone), HttpStatus.OK);
            }
            throw new GenericErrorException("Drone Id no found",
                    Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
        }catch (GenericErrorException e){
            Logger.getLogger(DroneController.class.getName()).log(Level.WARNING, e.getMessage());
            ResultDataResDto resultDataResDto = new ResultDataResDto();
            responseDto(resultDataResDto, e.getCodeError(),
                    e.getMessage(), e.getNameCodeError(), DroneController.class.getName());
            return new ResponseEntity<>(resultDataResDto, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            Logger.getLogger(DroneController.class.getName()).log(Level.WARNING, e.getMessage());
            ResultDataResDto resultDataResDto = new ResultDataResDto();
            responseDto(resultDataResDto, Constante.CODE_RESPONSE_SERVER_ERROR,
                    e.getMessage(), Constante.DESCRIPTION_CODE_RESPONSE_SERVER_ERROR, DroneController.class.getName());
            return new ResponseEntity<>(resultDataResDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @implNote checking loaded medication items for a given drone;
     * @param idDrone
     * @return
     */
    @GetMapping("/{idDrone}/medications")
    public ResponseEntity<Object> getAllMedicationItemsByIdDrone(@PathVariable Long idDrone){
        try{
            Optional droneOptional = droneRepository.findById(idDrone);
            if(droneOptional.isPresent()){
                Drone drone = (Drone) droneOptional.get();
                return new ResponseEntity<>(drone.getMedicationList(), HttpStatus.OK);
            }
            throw new GenericErrorException("Drone Id no found",
                    Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
        }catch (GenericErrorException e){
            Logger.getLogger(DroneController.class.getName()).log(Level.WARNING, e.getMessage());
            ResultDataResDto resultDataResDto = new ResultDataResDto();
            responseDto(resultDataResDto, e.getCodeError(),
                    e.getMessage(), e.getNameCodeError(), DroneController.class.getName());
            return new ResponseEntity<>(resultDataResDto, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            Logger.getLogger(DroneController.class.getName()).log(Level.SEVERE, e.getMessage());
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @implNote checking available drones for loading
     * @return
     */
    @GetMapping("/available")
    public ResponseEntity<List<Drone>> getAllDronesAvailable(){
        try{
            List<Drone> droneList = droneRepository.findAll().stream().filter(drone-> drone.getDroneStatus().equals(DroneStatus.IDLE)).collect(Collectors.toList());
            return new ResponseEntity<>(droneList, HttpStatus.OK);
        }catch (Exception e){
            Logger.getLogger(DroneController.class.getName()).log(Level.SEVERE, e.getMessage());
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @implNote check drone battery level for a given drone
     * @param idDrone
     * @return
     */
    @GetMapping("/{idDrone}/batterylevel")
    public ResponseEntity<Object> getBatteryLevelByIdDrone(@PathVariable Long idDrone){
        try{
            Optional droneOptional = droneRepository.findById(idDrone);
            if(droneOptional.isPresent()){
                Drone drone = (Drone) droneOptional.get();
                HashMap<String, Integer> batteryLevel = new HashMap<>();
                batteryLevel.put("batteryLevel", drone.getBattery());
                return new ResponseEntity<>(batteryLevel, HttpStatus.OK);
            }
            throw new GenericErrorException("Drone Id no found",
                    Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
        }catch (GenericErrorException e){
            Logger.getLogger(DroneController.class.getName()).log(Level.WARNING, e.getMessage());
            ResultDataResDto resultDataResDto = new ResultDataResDto();
            responseDto(resultDataResDto, e.getCodeError(),
                    e.getMessage(), e.getNameCodeError(), DroneController.class.getName());
            return new ResponseEntity<>(resultDataResDto, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            Logger.getLogger(DroneController.class.getName()).log(Level.SEVERE, e.getMessage());
            return  new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @implNote aux function generic for response
     * @param resultData
     * @param codeResponse
     * @param descriptionResponse
     * @param nameCodeResult
     * @param source
     */
    public static void responseDto(ResultDataResDto resultData, String codeResponse, String descriptionResponse, String nameCodeResult, String source){
        resultData.setCode(codeResponse);
        resultData.setName(nameCodeResult);
        resultData.setDescription(descriptionResponse);
        resultData.setSource(source);
    }

}
