package com.example.drones.entity;

import com.example.drones.enums.DroneModel;
import com.example.drones.enums.DroneStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Drone implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_drone")
    private Long idDrone;

    @NotNull(message = "serialNumber field is mandatory")
    @NotBlank(message = "serialNumber field must not be empty")
    @Size(max = 100, message = "value not valid, 100 characters max")
    @JsonProperty("serialNumber")
    @Column(name = "serial_number")
    private String serialNumber;

    @NotNull(message = "droneModel field is mandatory")
    @JsonProperty("droneModel")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "drone_model")
    private DroneModel droneModel;

    @NotNull(message = "weightLimit is mandatory")
    @JsonProperty("weightLimit")
    @Column(name = "weight_limit")
    private double weightLimit;

    @NotNull(message = "droneStatus field is mandatory")
    @JsonProperty("droneStatus")
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "drone_status")
    private DroneStatus droneStatus;

    @NotNull(message = "battery field is mandatory")
    @Digits(integer = 3, fraction = 0, message = "value not valid, only three digits.")
    private Integer battery;

    @Valid
    @NotNull(message = "medications field is mandatory")
    @JsonProperty("medications")
    @ManyToMany(fetch = FetchType.LAZY)
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "drone_medication", joinColumns = @JoinColumn(name = "id_drone"),
            inverseJoinColumns = @JoinColumn(name = "id_medication"))
    private List<Medication> medicationList;


}
