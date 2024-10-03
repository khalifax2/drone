package com.thedrone.aero.service.Impl;

import com.thedrone.aero.dto.MedicationDto;
import com.thedrone.aero.mapper.MedicationMapper;
import com.thedrone.aero.model.Medication;
import com.thedrone.aero.repository.MedicationRepository;
import com.thedrone.aero.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    @Override
    public MedicationDto add(MedicationDto medicationDto) {
        Medication newMedication = MedicationMapper.mapToMedication(medicationDto);
        medicationRepository.save(newMedication);
        return medicationDto;
    }

    @Override
    public void addAll(List<MedicationDto> medicationDtoList) {
        List<Medication> medicationList = medicationDtoList.stream()
                .map(MedicationMapper::mapToMedication).toList();

        medicationRepository.saveAll(medicationList);
    }

    @Override
    public List<Medication> findAllMedicationById(List<String> medicationIdList) {
        List<Medication> medicationList = medicationRepository.findAllById(medicationIdList);
        if (medicationList.isEmpty()) {
            throw new RuntimeException("Medications not found");
        }
        return medicationList;
    }

    @Override
    public List<MedicationDto> findAllMedicationByDroneId(String droneId) {
        List<Medication> medicationList = medicationRepository.findByDrone_DroneIdAndIsDelivered(droneId, false);
        return medicationList.stream()
                .map(MedicationMapper::mapToMedicationDto)
                .toList();
    }
}
