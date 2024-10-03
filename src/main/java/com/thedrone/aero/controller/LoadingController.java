package com.thedrone.aero.controller;

import com.thedrone.aero.dto.LoadingMedDto;
import com.thedrone.aero.service.LoadMedicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/load")
public class LoadingController {

    private final LoadMedicationService loadMedicationService;

    @PostMapping("/loadMedication")
    public ResponseEntity<String> loadMedication(@Valid @RequestBody LoadingMedDto loadingMedDto) throws InterruptedException {
        loadMedicationService.loadMedication(loadingMedDto);
        return ResponseEntity.status(HttpStatus.OK).body("Loaded successfully.");
    }
}
