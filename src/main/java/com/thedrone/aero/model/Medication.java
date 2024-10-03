package com.thedrone.aero.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String medicationId;
    private String name;
    private Integer weight;
    private String code;
    private String imagePath;

    @ColumnDefault(value="false")
    private Boolean isDelivered;

    @ManyToOne
    @JoinColumn(name = "drone_id", referencedColumnName = "droneId")
    private Drone drone;

}
