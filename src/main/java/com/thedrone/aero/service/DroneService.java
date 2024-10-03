package com.thedrone.aero.service;


import com.thedrone.aero.dto.DroneDto;
import com.thedrone.aero.model.Drone;

public interface DroneService {
    DroneDto register(DroneDto droneDto);
    Drone findDroneById(String droneId);
    Drone updateDrone(Drone drone);
    String batteryInfo(String droneId);
    String availability(String droneId);
}
