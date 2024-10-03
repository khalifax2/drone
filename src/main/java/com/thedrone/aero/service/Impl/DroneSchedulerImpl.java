package com.thedrone.aero.service.Impl;

import com.thedrone.aero.enumerated.State;
import com.thedrone.aero.model.Drone;
import com.thedrone.aero.service.DroneScheduler;
import com.thedrone.aero.service.DroneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.thedrone.aero.enumerated.State.*;

@Service
@Slf4j
public class DroneSchedulerImpl implements DroneScheduler {

    private final ConcurrentHashMap<String, ScheduledExecutorService> activeSchedulers = new ConcurrentHashMap<>();
    private DroneService droneService;

    public DroneSchedulerImpl(DroneService droneService) {
        this.droneService = droneService;
    }

    @Override
    public void startScheduler(String droneId) {
        Drone drone = droneService.findDroneById(droneId);

        ScheduledExecutorService scheduler = activeSchedulers.putIfAbsent(droneId, Executors.newScheduledThreadPool(1));

        if (scheduler != null) {
            log.error("Scheduler for drone {} is already running.", droneId);
            throw new RuntimeException("Scheduler for drone " + droneId + " is already running.");
        }

        if (!State.LOADED.equals(drone.getState())) {
            throw new RuntimeException("Drone state must be LOADED.");
        }

        log.info("Scheduler started...");

        activeSchedulers.get(droneId).scheduleAtFixedRate(() -> changeDroneState(drone),1,2, TimeUnit.SECONDS);
    }

    private void changeDroneState(Drone drone) {
        switch (drone.getState()) {
            case LOADED -> {
                drone.setState(DELIVERING);
                droneService.updateDrone(drone);
                log.info("Drone state: {}", DELIVERING);
            }
            case DELIVERING -> {
                drone.setState(State.DELIVERED);
                droneService.updateDrone(drone);
                log.info("Drone state: {}", State.DELIVERED);
            }
            case DELIVERED -> {
                drone.setState(RETURNING);
                droneService.updateDrone(drone);
                log.info("Drone state: {}", State.RETURNING);
            }
            case RETURNING -> {
                drone.setState(State.IDLE);
                drone.setBatteryCapacity(drone.getBatteryCapacity() - 50);
                drone.getMedications().forEach(medication -> medication.setIsDelivered(true));
                droneService.updateDrone(drone);
                log.info("Drone state: {}", State.IDLE);
                stopAndRemoveScheduler(drone.getDroneId());
            }
            default -> {
                log.error("Drone invalid state.");
                stopAndRemoveScheduler(drone.getDroneId());
                throw new RuntimeException("Drone invalid state..");
            }
        }
    }

    private void stopScheduler(String droneId) {
        ScheduledExecutorService scheduler = activeSchedulers.get(droneId);
        if (scheduler == null) return;
        scheduler.shutdown();
        log.info("Scheduler stop for drone {}.", droneId);
    }

   private void stopAndRemoveScheduler(String droneId) {
        stopScheduler(droneId);
        activeSchedulers.remove(droneId);
        log.info("Removing active scheduler for drone {}.", droneId);
   }
}
