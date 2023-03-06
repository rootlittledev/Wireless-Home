package com.example.wirelesshome.connector;

import com.example.wirelesshome.connector.switchbot.SwitchBotConnector;
import com.example.wirelesshome.connector.tuya.TuyaConnector;
import com.example.wirelesshome.exception.DeviceNotSupported;
import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.light.Light;
import com.example.wirelesshome.model.device.light.LightColor;
import com.example.wirelesshome.model.device.light.LightStateRequest;
import com.example.wirelesshome.model.device.tuya.Command;
import com.example.wirelesshome.model.device.tuya.TuyaCommand;
import com.example.wirelesshome.util.CommandUtils;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class LightConnector {

    private final ModelMapper mapper;

    private final TuyaConnector tuyaConnector;
    private final SwitchBotConnector switchBotConnector;

    public LightConnector(ModelMapper mapper, @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") TuyaConnector tuyaConnector, SwitchBotConnector switchBotConnector) {
        this.mapper = mapper;
        this.tuyaConnector = tuyaConnector;
        this.switchBotConnector = switchBotConnector;
    }

    public LightStateRequest getDevice(Light light) {
        DeviceManufacturer manufacturer = light.getManufacturer();
        Connector connector = getConnector(manufacturer);

        return getLight(connector.getDevice(light.getId()), manufacturer);
    }

    private LightStateRequest getLight(Object device, DeviceManufacturer manufacturer) {
        if (DeviceManufacturer.TUYA.equals(manufacturer)) {
            return getLightStateRequest(device);
        } else {
            throw new DeviceNotSupported();
        }
    }

    @NotNull
    private LightStateRequest getLightStateRequest(Object device) {
        Command[] tuyaDevice = mapper.map(device, Command[].class);

        Command state = CommandUtils.getCommand(Arrays.asList(tuyaDevice), TuyaCommand.LIGHT);

        Command brightnessState = CommandUtils.getCommand(Arrays.asList(tuyaDevice), TuyaCommand.BRIGHTNESS);

        DeviceState status = (Boolean) state.getValue() ? DeviceState.ON : DeviceState.OFF;
        Long brightness = Math.round((Double) brightnessState.getValue());

        return new LightStateRequest(brightness, status, LightColor.WHITE);
    }

    private Connector getConnector(DeviceManufacturer manufacturer) {
        if (DeviceManufacturer.TUYA.equals(manufacturer)) {
            return tuyaConnector;
        } else if (DeviceManufacturer.SWITCHBOT.equals(manufacturer)) {
            return switchBotConnector;
        } else {
            throw new DeviceNotSupported();
        }
    }

    public Boolean commands(Light light) {
        Connector connector = getConnector(light.getManufacturer());

        return connector.commands(light.getId(), CommandUtils.getLightCommands(light));
    }


}
