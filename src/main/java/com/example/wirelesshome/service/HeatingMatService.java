package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.DeviceStateMissing;
import com.example.wirelesshome.exception.HeatingMatNotFound;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.mat.HeatingMat;
import com.example.wirelesshome.model.device.mat.HeatingMatStateRequest;
import com.example.wirelesshome.repository.HeatingMatRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class HeatingMatService {

    private final SchedulerService schedulerService;
    private final HeatingMatRepo repo;

    public HeatingMatService(SchedulerService schedulerService, HeatingMatRepo repo) {
        this.schedulerService = schedulerService;
        this.repo = repo;
    }

    public List<HeatingMat> getAll() {

        return repo.findAll();
    }

    public HeatingMat save(HeatingMat heatingMat) {
        return repo.save(heatingMat);
    }

    public HeatingMat getHeatingMat(String name) {
        return repo.findByName(name)
                .orElseThrow(() -> new HeatingMatNotFound(name));
    }

    public HeatingMat update(String name, HeatingMatStateRequest heatingMatState) {
        HeatingMat heatingMat = getHeatingMat(name);
        DeviceState state = heatingMatState.getState();
        heatingMat.setState(state);

        if (state != null) {
            log.debug("Heating Mat State set to {}", state);
        } else {
            throw new DeviceStateMissing("Heating Mat " + name + " is missing state");
        }

        if (DeviceState.ON.equals(state)) {
            log.debug("Heating Mat On setting default temperature to 20");
            heatingMat.setTemperature(20L);
        } else {
            log.debug("Heating Mat Off setting default temperature to 0");
            heatingMat.setTemperature(0L);
        }

        Long temperature = heatingMatState.getTemperature();
        if (temperature != null && DeviceState.ON.equals(state)) {
            log.debug("Heating Mat temperature is {}", temperature);
            heatingMat.setTemperature(temperature);
        } else {
            log.debug("Heating Mat temperature missing, using default");
        }

        LocalDateTime shutOff = schedulerService.schedule(name, "mat", heatingMatState.getTimer());

        heatingMat.setShutOff(shutOff);

        return repo.save(heatingMat);
    }

    public void delete(String name) {
        repo.deleteById(name);
    }

    public void disable(String name) {
        HeatingMat heatingMat = getHeatingMat(name);
        heatingMat.setShutOff(null);
        heatingMat.setState(DeviceState.OFF);

        repo.save(heatingMat);

    }

}
