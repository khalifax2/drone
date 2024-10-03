package com.thedrone.aero.mapper;

import com.thedrone.aero.dto.DroneDto;
import com.thedrone.aero.model.Drone;

public class DroneMapper {

    public static Drone mapToDrone(DroneDto droneDto) {
        return Drone.builder()
                .serialNo(droneDto.getSerialNo())
                .model(droneDto.getModel())
                .weightLimit(droneDto.getModel().getMaxCapacity())
                .batteryCapacity(droneDto.getBatteryCapacity())
                .build();
    }

}
