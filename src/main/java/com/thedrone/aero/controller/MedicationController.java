package com.thedrone.aero.controller;

import com.thedrone.aero.dto.MedicationDto;
import com.thedrone.aero.service.MedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/medication")
public class MedicationController {

    private final MedicationService medicationService;

    @PostMapping("/add")
    public ResponseEntity<String> addMedication(@Valid @RequestBody MedicationDto medicationDto) {
        medicationService.add(medicationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added successfully.");
    }

    @PostMapping("/addAll")
    public ResponseEntity<String> addMedication(@Valid @RequestBody List<MedicationDto> medicationDto) {
        medicationService.addAll(medicationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Added successfully.");
    }
}
