package com.example.drones.controller;

import com.example.drones.constante.Constante;
import com.example.drones.dto.ResultDataResDto;
import com.example.drones.entity.Drone;
import com.example.drones.entity.Medication;
import com.example.drones.enums.DroneStatus;
import com.example.drones.exceptions.GenericErrorException;
import com.example.drones.services.IDroneService;
import com.example.drones.services.IMedicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/drone")
public class DroneController {

    @Autowired
    private IDroneService droneService;

    @Autowired
    private IMedicationService medicationService;

    /**
     * @implNote registering a drone
     * @param drone information structure to create drone with medication items
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<Object> save(@Valid @RequestBody Drone drone){
        try{
            double weightFull = 0D;
            List<Medication> medicationList = new ArrayList<>();
            for (Medication medicationItem : drone.getMedicationList()) {
                if(medicationItem.getIdMedication() != null){
                    Medication medication = medicationService.findById(medicationItem.getIdMedication());
                    if (medication != null){
                        medicationList.add(medication);
                    }else if(!medicationService.existMedicationByCodeAndName(medicationItem.getName(), medicationItem.getCode())){
                        medicationList.add(medicationService.save(medicationItem));
                    }else{
                        throw new GenericErrorException("You are trying to create a Medication with a name and code that already exists in the database, add the corresponding idMedication parameter to the structure",
                                Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
                    }
                }else if(!medicationService.existMedicationByCodeAndName(medicationItem.getName(), medicationItem.getCode())){
                    medicationList.add(medicationService.save(medicationItem));
                }else{
                    throw new GenericErrorException("You are trying to create a Medication with a name and code that already exists in the database, add the corresponding idMedication parameter to the structure",
                            Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
                }
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
            if(droneService.existDroneBySerialNumber(drone.getSerialNumber())){
                throw new GenericErrorException("There is a Drone with that serial number",
                        Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
            }
            drone.setMedicationList(medicationList);
            return new ResponseEntity<>(droneService.save(drone), HttpStatus.CREATED);
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
            return new ResponseEntity<>(droneService.findAll(), HttpStatus.OK);
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
            Optional droneOptional = droneService.findById(idDrone);
            if(droneOptional.isPresent()){
                Drone drone = (Drone) droneOptional.get();
                double weightFull = 0D;
                List<Medication> medicationList = new ArrayList<>();
                for (Medication medicationItem : medicationItems) {
                    if(medicationItem.getIdMedication() != null){
                        Medication medication = medicationService.findById(medicationItem.getIdMedication());
                        if (medication != null){
                            medicationList.add(medication);
                        }else if(!medicationService.existMedicationByCodeAndName(medicationItem.getName(), medicationItem.getCode())){
                            medicationList.add(medicationService.save(medicationItem));
                        }else{
                            throw new GenericErrorException("You are trying to create a Medication with a name and code that already exists in the database, add the corresponding idMedication parameter to the structure",
                                    Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
                        }
                    }else if(!medicationService.existMedicationByCodeAndName(medicationItem.getName(), medicationItem.getCode())){
                        medicationList.add(medicationService.save(medicationItem));
                    }else{
                        throw new GenericErrorException("You are trying to create a Medication with a name and code that already exists in the database, add the corresponding idMedication parameter to the structure",
                                Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
                    }
                    weightFull += medicationItem.getWeight();
                }
                if(weightFull > drone.getWeightLimit()){
                    throw new GenericErrorException("Drone charge limit exceeded",
                            Constante.CODE_RESPONSE_ARGUMENT_NOT_VALID, Constante.NAME_CODE_RESPONSE_ARGUMENT_NOT_VALID);
                }
                drone.setMedicationList(medicationList);

                return new ResponseEntity<>(droneService.save(drone), HttpStatus.OK);
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
            Optional droneOptional = droneService.findById(idDrone);
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
            List<Drone> droneList = droneService.findAllAvailable();
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
            Optional droneOptional = droneService.findById(idDrone);
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
