package com.example.drones.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Audit  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_audit")
    private Long idAudit;

    @Column(name = "log_date_time")
    private LocalDateTime logDateTime;

    @Column(name = "drone_serial_number")
    private String droneSerialNumber;

    private Integer battery;
}
