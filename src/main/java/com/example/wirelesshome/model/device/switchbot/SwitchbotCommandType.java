package com.example.wirelesshome.model.device.switchbot;

public enum SwitchbotCommandType {
    COMMAND("command"), OTHER("customize");

    private final String commandType;

    SwitchbotCommandType(String commandType) {

        this.commandType = commandType;
    }

    public String getCommandType() {
        return commandType;
    }
}
