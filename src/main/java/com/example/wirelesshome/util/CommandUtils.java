package com.example.wirelesshome.util;

import com.example.wirelesshome.exception.CommandNotFound;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.light.Light;
import com.example.wirelesshome.model.device.tuya.Command;
import com.example.wirelesshome.model.device.tuya.TuyaCommand;
import com.example.wirelesshome.model.device.tuya.TuyaCommandRequest;

import java.util.ArrayList;
import java.util.List;

public final class CommandUtils {

    private CommandUtils() {
        throw new UnsupportedOperationException();
    }


    public static TuyaCommandRequest getLightCommands(Light light) {


        Command state = new Command(TuyaCommand.LIGHT.getCommand(), getCommandState(light));
        int value = Math.round(light.getBrightness() * 2.3f + 25f);
        Command brightness = new Command(TuyaCommand.BRIGHTNESS.getCommand(), value);

        List<Command> commands = new ArrayList<>();
        commands.add(state);
        commands.add(brightness);

        return new TuyaCommandRequest(commands);
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
