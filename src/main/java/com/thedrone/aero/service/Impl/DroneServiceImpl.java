package com.thedrone.aero.service.Impl;

import com.thedrone.aero.dto.DroneDto;
import com.thedrone.aero.enumerated.State;
import com.thedrone.aero.exceptions.AlreadyExistsException;
import com.thedrone.aero.mapper.DroneMapper;
import com.thedrone.aero.model.Drone;
import com.thedrone.aero.repository.DroneRepository;
import com.thedrone.aero.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final DroneRepository droneRepository;

    @Override
    public DroneDto register(DroneDto droneDto) {
        validateDrone(droneDto);
        Drone newDrone = DroneMapper.mapToDrone(droneDto);
        newDrone.setState(State.IDLE);
        newDrone.setBatteryCapacity(100);
        droneRepository.save(newDrone);
        return droneDto;
    }

    private void validateDrone(DroneDto droneDto) {
        List<Drone> droneList = droneRepository.findAll();
        if (droneList.size() == 10) {
            throw new RuntimeException("Drone max count is 10");
        }

        Optional<Drone> droneMatched = droneList.stream()
                .filter(drone -> drone.getSerialNo().equals(droneDto.getSerialNo()))
                .findFirst();

        if (droneMatched.isPresent()) {
            throw new AlreadyExistsException("Drone with serialNo: " + droneMatched.get().getSerialNo() + " already exists.");
        }
    }

    @Override
    public Drone findDroneById(String droneId) {
        return droneRepository.findById(droneId).orElseThrow(() -> {
            throw new RuntimeException("Drone with id: " + droneId + " not found." );
        });
    }

    @Override
    public Drone updateDrone(Drone drone) {
        return droneRepository.save(drone);
    }

    @Override
    public String batteryInfo(String droneId) {
        return findDroneById(droneId).getBatteryCapacity() + "%";
    }

    @Override
    public String availability(String droneId) {
        Drone drone = findDroneById(droneId);
        if (drone.getBatteryCapacity() < 25) {
            return "Drone is not available. Please charge current battery is below 25%. Battery level: " + drone.getBatteryCapacity() + "%";
        }
        if (State.IDLE.equals(drone.getState())) {
            return "Drone is available for loading.";
        }
        return "Drone is not available for loading. Current state: " + drone.getState();
    }
}
