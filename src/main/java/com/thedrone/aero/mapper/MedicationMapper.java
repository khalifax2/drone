package com.thedrone.aero.mapper;

import com.thedrone.aero.dto.MedicationDto;
import com.thedrone.aero.model.Medication;

public class MedicationMapper {

    public static Medication mapToMedication(MedicationDto medicationDto) {
        return Medication.builder()
                .name(medicationDto.getName())
                .weight(medicationDto.getWeight())
                .code(medicationDto.getCode())
                .imagePath(medicationDto.getImagePath())
                .isDelivered(false)
                .build();
    }

    public static MedicationDto mapToMedicationDto(Medication medication) {
        return MedicationDto.builder()
                .name(medication.getName())
                .weight(medication.getWeight())
                .code(medication.getCode())
                .imagePath(medication.getImagePath())
                .build();
    }
}
