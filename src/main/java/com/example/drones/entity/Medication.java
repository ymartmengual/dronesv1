package com.example.drones.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Medication implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_medication")
    private Long idMedication;

    @NotNull(message = "name field is mandatory")
    @NotBlank(message = "name field must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9-_]+$", message = "value not valid, allowed only letters, numbers, ‘-‘, ‘_’")
    private String name;

    @NotNull(message = "weight field is mandatory")
    private double weight;

    @NotNull(message = "code field is mandatory")
    @NotBlank(message = "code field must not be empty")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "value not valid, allowed only upper case letters, underscore and numbers")
    private String code;

    @Size(max = 5120)
    private String image;

}
