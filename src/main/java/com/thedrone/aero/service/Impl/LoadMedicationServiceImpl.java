package com.thedrone.aero.service.Impl;

import com.thedrone.aero.dto.LoadingMedDto;
import com.thedrone.aero.enumerated.State;
import com.thedrone.aero.model.Drone;
import com.thedrone.aero.model.Medication;
import com.thedrone.aero.service.DroneService;
import com.thedrone.aero.service.LoadMedicationService;
import com.thedrone.aero.service.MedicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoadMedicationServiceImpl implements LoadMedicationService {

    private final DroneService droneService;
    private final MedicationService medicationService;


    @Override
    public void loadMedication(LoadingMedDto loadingMedDto) throws InterruptedException {
        Drone drone = droneService.findDroneById(loadingMedDto.getDroneId());
        List<Medication> medicationList = medicationService.findAllMedicationById(loadingMedDto.getMedicationIds());

        validateLoad(drone, medicationList);

        drone.setState(State.LOADING);
        droneService.updateDrone(drone);
        log.info("Drone state: {}", State.LOADING);

        Thread.sleep(1000); // Sleep for 1 second when loading

        drone.setMedications(medicationList);
        drone.setState(State.LOADED);
        droneService.updateDrone(drone);
        log.info("Drone state: {}", State.LOADED);

    }

    private void validateLoad(Drone drone, List<Medication> medicationList) {

        if (!State.IDLE.equals(drone.getState())) {
            throw new RuntimeException("Drone with id: " + drone.getDroneId() + " is not available.");
        }

        if (drone.getBatteryCapacity() < 25) {
            throw new RuntimeException("Warning: Please charge current battery is below 25%. Battery level: " + drone.getBatteryCapacity() + "%");
        }

        int currentWeight = drone.getWeightLimit();
        List<String> alreadyLoadedMedList = new ArrayList<>();

        for (Medication medication : medicationList) {
            currentWeight -= medication.getWeight();
            if (currentWeight < 0) {
                throw new RuntimeException("Exceed weight limit. Available is only " + drone.getWeightLimit());
            }
            if (medication.getDrone() != null) {
                alreadyLoadedMedList.add(medication.getMedicationId());
            } else {
                medication.setDrone(drone);
            }

        }

        if (!alreadyLoadedMedList.isEmpty()) {
            String errorIds = alreadyLoadedMedList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(", "));

            throw new RuntimeException("Medication ids: [" + errorIds + "] are not available.");
        }
    }
}
