package com.example.wirelesshome.service;

import com.example.wirelesshome.component.BoundComponent;
import com.example.wirelesshome.connector.TemperatureConnector;
import com.example.wirelesshome.exception.CommandFailed;
import com.example.wirelesshome.exception.ThermostatNotFound;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.thermometer.Thermometer;
import com.example.wirelesshome.model.device.thermometer.ThermometerStateRequest;
import com.example.wirelesshome.model.device.thermostat.*;
import com.example.wirelesshome.repository.ThermostatRepo;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ThermometerService {

    private final TemperatureConnector connector;

    private final ThermostatRepo repo;

    private final BoundComponent boundComponent;

    public ThermometerService(TemperatureConnector connector, ThermostatRepo repo, BoundComponent boundComponent) {
        this.connector = connector;
        this.repo = repo;
        this.boundComponent = boundComponent;
    }

    public List<Thermostat> getAll() {

        return null;
    }

    public Thermometer getTemperature(String id) {
        Thermostat thermostat = repo.findById(id).orElseThrow(ThermostatNotFound::new);


        return getThermometer(thermostat);
    }

    @NotNull
    private Thermometer getThermometer(Thermostat thermostat) {
        Thermometer thermometer = thermostat.getThermometer();
        ThermometerStateRequest states = connector.getDevice(thermometer);

        thermometer.setTemperature(states.getTemperature());
        thermometer.setHumidity(states.getHumidity());

        return thermometer;
    }

    public Thermostat save(Thermostat thermostat) {
        return repo.save(thermostat);
    }

    public Thermostat update(String id, ThermostatStateRequest request) {

        Thermostat thermostat = repo.findById(id).orElseThrow(ThermostatNotFound::new);

        thermostat.setMode(request.getMode());
        thermostat.setState(request.getState());
        thermostat.setDesiredTemperature(request.getDesiredTemperature());
        thermostat.setFanSpeed(request.getFanSpeed());


        if (connector.commands(thermostat)) {
            return repo.save(thermostat);
        } else {
            throw new CommandFailed();
        }
    }

    public Thermostat updateBound(BoundRequest request) {

        Thermostat thermostat = repo.findById(request.getDeviceId()).orElseThrow(ThermostatNotFound::new);

        thermostat.setBound(request.isEnabled());
        thermostat.setLowBound(request.getLowBound());
        thermostat.setHighBound(request.getHighBound());

        boundComponent.register(thermostat, () -> checkBound(thermostat));

        return repo.save(thermostat);
    }

    private void checkBound(Thermostat thermostat) {
        Thermometer thermometer = getThermometer(thermostat);
        if (thermometer.getTemperature() > thermostat.getHighBound()) {
            log.debug("Temperature higher than the bound, turning on COOLING for device: {}", thermostat.getName());

            update(thermostat.getId(), new ThermostatStateRequest(DeviceState.ON, thermostat.getDesiredTemperature(), FanSpeed.AUTO, ThermostatMode.COOLING));
        } else if (thermometer.getTemperature() < thermostat.getLowBound()) {
            log.debug("Temperature lower than the bound, turning on HEATING for device: {}", thermostat.getName());

            update(thermostat.getId(), new ThermostatStateRequest(DeviceState.ON, thermostat.getDesiredTemperature(), FanSpeed.AUTO, ThermostatMode.HEATING));
        } else {
            log.debug("Temperature in bounds");
        }

        thermostat.setThermometer(thermometer);
    }

}
