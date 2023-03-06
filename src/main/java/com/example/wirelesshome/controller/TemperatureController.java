package com.example.wirelesshome.controller;

import com.example.wirelesshome.model.device.thermometer.Thermometer;
import com.example.wirelesshome.model.device.thermostat.Thermostat;
import com.example.wirelesshome.service.ThermometerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/temperature")
public class TemperatureController {

    private final ThermometerService service;

    public TemperatureController(ThermometerService service) {
        this.service = service;
    }

    @GetMapping
    List<Thermostat> getThermostats() {

        return service.getAll();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    Thermostat saveThermostat(@RequestBody Thermostat thermostat) {

        return service.save(thermostat);

    }

    @GetMapping("/{deviceId}")
    Thermometer getThermostats(@PathVariable String deviceId) {

        return service.getTemperature(deviceId);

    }
}
