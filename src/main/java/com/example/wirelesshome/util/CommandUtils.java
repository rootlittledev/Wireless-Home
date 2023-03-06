package com.example.wirelesshome.util;

import com.example.wirelesshome.exception.CommandNotFound;
import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.light.Light;
import com.example.wirelesshome.model.device.light.LightColor;
import com.example.wirelesshome.model.device.switchbot.SwitchbotCommand;
import com.example.wirelesshome.model.device.switchbot.SwitchbotCommandRequest;
import com.example.wirelesshome.model.device.switchbot.SwitchbotCommandType;
import com.example.wirelesshome.model.device.thermostat.Thermostat;
import com.example.wirelesshome.model.device.tuya.Command;
import com.example.wirelesshome.model.device.tuya.TuyaColor;
import com.example.wirelesshome.model.device.tuya.TuyaCommand;
import com.example.wirelesshome.model.device.tuya.TuyaCommandRequest;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public final class CommandUtils {

    private CommandUtils() {
        throw new UnsupportedOperationException();
    }


    public static Object getLightCommands(Light light) {

        if (DeviceManufacturer.TUYA.equals(light.getManufacturer())){
            return getTuyaCommandRequest(light);
        } else if (DeviceManufacturer.SWITCHBOT.equals(light.getManufacturer())) {
            return getSwitchbotCommandRequest(light);
        } else {
            throw new CommandNotFound();
        }
    }

    @NotNull
    private static SwitchbotCommandRequest getSwitchbotCommandRequest(Light light) {
        SwitchbotCommandRequest commandRequest = new SwitchbotCommandRequest();

        SwitchbotCommand switchbotCommand = DeviceState.ON.equals(light.getState()) ? SwitchbotCommand.TURN_ON : SwitchbotCommand.TURN_OFF;

        commandRequest.setCommandType(SwitchbotCommandType.COMMAND.getCommandType());
        commandRequest.setCommand(switchbotCommand.getCommand());
        commandRequest.setParameter("default");

        return commandRequest;
    }

    @NotNull
    private static TuyaCommandRequest getTuyaCommandRequest(Light light) {
        Command state = new Command(TuyaCommand.LIGHT.getCommand(), getCommandState(light));
        int value = Math.round(light.getBrightness() * 2.3f + 25f);
        Command brightness = new Command(TuyaCommand.BRIGHTNESS.getCommand(), value);


        List<Command> commands = new ArrayList<>();
        commands.add(state);
        commands.add(brightness);
        if (LightColor.WHITE.equals(light.getColor())) {
            Command colorMode = new Command(TuyaCommand.WORK_MODE.getCommand(), "white");
            commands.add(colorMode);
        } else {
            Command colorMode = new Command(TuyaCommand.WORK_MODE.getCommand(), "colour");
            Command color = new Command(TuyaCommand.COLOR.getCommand(), getColor(light.getColor()));
            commands.add(colorMode);
            commands.add(color);
        }


        return new TuyaCommandRequest(commands);
    }

    public static SwitchbotCommandRequest getThermostatCommands(Thermostat thermostat) {
        String command = thermostat.getDesiredTemperature() + "," +
                thermostat.getMode().getMode() + "," +
                thermostat.getFanSpeed().getSpeed() + "," +
                thermostat.getState().name().toLowerCase();

        return new SwitchbotCommandRequest(SwitchbotCommandType.COMMAND.getCommandType(), SwitchbotCommand.AC.getCommand(), command);
    }

    @NotNull
    private static TuyaColor getColor(LightColor lightColor) {

        switch (lightColor) {
            case RED: {
                return new TuyaColor("0", "255", "255");
            }
            case BLUE: {
                return new TuyaColor("240", "255", "255");
            }
            case GREEN: {
                return new TuyaColor("120", "255", "255");
            }
            case YELLOW: {
                return new TuyaColor("60", "255", "255");
            }
            default: {
                return new TuyaColor("0", "0", "255");
            }
        }
    }

    public static Command getCommand(List<Command> commands, TuyaCommand tuyaCommand) {
        return commands.stream()
                .filter(command -> tuyaCommand.getCommand().equals(command.getCode())).findFirst()
                .orElseThrow(CommandNotFound::new);
    }

    private static boolean getCommandState(Light light) {
        return DeviceState.ON.equals(light.getState());
    }
}
