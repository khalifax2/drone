package com.thedrone.aero.repository;


import com.thedrone.aero.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DroneRepository extends JpaRepository<Drone, String> {
    Optional<Drone> findBySerialNo(String serialNo);
}
