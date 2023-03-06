package com.example.wirelesshome.connector;

import com.example.wirelesshome.connector.switchbot.SwitchBotConnector;
import com.example.wirelesshome.exception.DeviceNotSupported;
import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.switchbot.SwitchbotThermometer;
import com.example.wirelesshome.model.device.thermometer.Thermometer;
import com.example.wirelesshome.model.device.thermometer.ThermometerStateRequest;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class TemperatureConnector {

    private final ModelMapper mapper;

    private final SwitchBotConnector switchBotConnector;

    public TemperatureConnector(ModelMapper mapper, SwitchBotConnector switchBotConnector) {
        this.mapper = mapper;
        this.switchBotConnector = switchBotConnector;
    }

    public ThermometerStateRequest getDevice(Thermometer thermometer) {
        DeviceManufacturer manufacturer = thermometer.getManufacturer();
        Connector connector = getConnector(manufacturer);

        return getThermometer(connector.getDevice(thermometer.getId()), manufacturer);
    }

    private ThermometerStateRequest getThermometer(Object device, DeviceManufacturer manufacturer) {
        if (DeviceManufacturer.SWITCHBOT.equals(manufacturer)) {
            return getThermometerStateRequest(device);
        } else {
            throw new DeviceNotSupported();
        }
    }

    @NotNull
    private ThermometerStateRequest getThermometerStateRequest(Object device) {
        SwitchbotThermometer switchbotThermometer = mapper.map(device, SwitchbotThermometer.class);

        return new ThermometerStateRequest(switchbotThermometer.getTemperature(), switchbotThermometer.getHumidity());
    }

    private Connector getConnector(DeviceManufacturer manufacturer) {
        if (DeviceManufacturer.SWITCHBOT.equals(manufacturer)) {
            return switchBotConnector;
        } else {
            throw new DeviceNotSupported();
        }
    }

    /*public Boolean commands(Thermostat light) {
     *//*Connector connector = getConnector(light.getManufacturer());

        return connector.commands(light.getId(), CommandUtils.getThermostatCommands(light));*//*

        return false;
    }*/


}
