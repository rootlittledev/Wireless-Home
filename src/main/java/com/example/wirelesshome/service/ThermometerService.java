package com.example.wirelesshome.service;

import com.example.wirelesshome.connector.TemperatureConnector;
import com.example.wirelesshome.exception.ThermostatNotFound;
import com.example.wirelesshome.model.device.thermometer.Thermometer;
import com.example.wirelesshome.model.device.thermometer.ThermometerStateRequest;
import com.example.wirelesshome.model.device.thermostat.Thermostat;
import com.example.wirelesshome.repository.ThermostatRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThermometerService {

    private final TemperatureConnector connector;

    private final ThermostatRepo repo;

    public ThermometerService(TemperatureConnector connector, ThermostatRepo repo) {
        this.connector = connector;
        this.repo = repo;
    }

    public List<Thermostat> getAll() {

        return null;
    }

    public Thermometer getTemperature(String id) {
        Thermostat thermostat = repo.findById(id).orElseThrow(ThermostatNotFound::new);


        Thermometer thermometer = thermostat.getThermometer();
        ThermometerStateRequest states = connector.getDevice(thermometer);

        thermometer.setTemperature(states.getTemperature());
        thermometer.setHumidity(states.getHumidity());

        return thermometer;
    }

    public Thermostat save(Thermostat thermostat) {
        return repo.save(thermostat);
    }
}
