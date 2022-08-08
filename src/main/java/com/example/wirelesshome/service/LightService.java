package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.DeviceStateMissing;
import com.example.wirelesshome.exception.LightNotFound;
import com.example.wirelesshome.model.device.light.Light;
import com.example.wirelesshome.model.device.light.LightColor;
import com.example.wirelesshome.model.device.light.LightStateRequest;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.repository.LightRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LightService {

    private final LightRepo repo;

    public LightService(LightRepo repo) {
        this.repo = repo;
    }

    public List<Light> getAll() {
        return repo.findAll();
    }

    public Light save(Light light) {
        return repo.save(light);
    }

    public Light update(Light light) {
        return repo.save(light);
    }

    public Light getLight(String name) {
        return repo.findByName(name)
                .orElseThrow(() -> new LightNotFound(name));
    }
    public Light update(String name, LightStateRequest lightState) {
        Light light = getLight(name);
        DeviceState state = lightState.getState();
        light.setState(state);

        if (state != null) {
            log.debug("Light State set to {}", state);
        } else {
            throw new DeviceStateMissing("Light " + name + " is missing state");
        }

        if (DeviceState.ON.equals(state)) {
            log.debug("Light On setting default brightness to 100");
            light.setBrightness(100L);
        } else {
            log.debug("Light Off setting default brightness to 0");
            light.setBrightness(0L);
        }

        Long brightness = lightState.getBrightness();
        if (brightness != null && DeviceState.ON.equals(state)) {
            log.debug("Light brightness is {}", brightness);
            light.setBrightness(brightness);
        } else {
            log.debug("Light brightness missing, using default");
        }

        LightColor color = lightState.getColor();
        if (color != null) {
            log.debug("Light color is {}", color);
            light.setColor(color);
        } else {
            log.debug("Light color missing, using default");
        }

        return repo.save(light);
    }

}
