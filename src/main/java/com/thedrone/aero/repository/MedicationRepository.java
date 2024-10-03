package com.thedrone.aero.repository;

import com.thedrone.aero.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, String> {
    List<Medication> findByDrone_DroneId(String droneId);
}
