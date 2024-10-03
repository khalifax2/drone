package com.thedrone.aero.controller;

import com.thedrone.aero.dto.DroneDto;
import com.thedrone.aero.dto.MedicationDto;
import com.thedrone.aero.model.Drone;
import com.thedrone.aero.service.DroneScheduler;
import com.thedrone.aero.service.DroneService;
import com.thedrone.aero.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/drone")
public class DroneController {

    private final DroneService droneService;
    private final MedicationService medicationService;
    private final DroneScheduler droneScheduler;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody DroneDto droneDto) {
        DroneDto savedDrone = droneService.register(droneDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Added Successfully.");
    }

    @GetMapping("/{droneId}/loadedMedication")
    public ResponseEntity<List<MedicationDto>> loadedMedication(@PathVariable String droneId) {
        List<MedicationDto> medicationDtoList = medicationService.findAllMedicationByDroneId(droneId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(medicationDtoList);
    }

    @GetMapping("/scheduler")
    public ResponseEntity<String> scheduler(@RequestParam String droneId) {
        droneScheduler.startScheduler(droneId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Drone scheduler started");
    }

    @GetMapping("/batteryInfo")
    public ResponseEntity<String> batteryInfo(@RequestParam String droneId) {
        String batteryLevel = droneService.batteryInfo(droneId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("Drone battery level: " + batteryLevel);
    }

    @GetMapping("/availability")
    public ResponseEntity<String> availability(@RequestParam String droneId) {
        String droneState = droneService.availability(droneId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(droneState);
    }
}
