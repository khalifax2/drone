package com.thedrone.aero.model;

import com.thedrone.aero.enumerated.Model;
import com.thedrone.aero.enumerated.State;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Drone {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String droneId;

    private String serialNo;

    @Enumerated(EnumType.STRING)
    private Model model;

    private Integer weightLimit;
    private Integer batteryCapacity;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy = "drone", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Medication> medications;

}
