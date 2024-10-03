package com.thedrone.aero.service;

import com.thedrone.aero.dto.MedicationDto;
import com.thedrone.aero.model.Medication;

import java.util.List;

public interface MedicationService {
    MedicationDto add(MedicationDto medicationDto);
    void addAll(List<MedicationDto> medicationDtoList);
    List<Medication> findAllMedicationById(List<String> medicationIdList);
    List<MedicationDto> findAllMedicationByDroneId(String droneId);
}
