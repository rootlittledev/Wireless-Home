package com.example.wirelesshome.model.device.switchbot;

public enum SwitchbotCommand {
    AC("setAll"), TURN_ON("turnOn"), TURN_OFF("turnOff");

    private final String command;

    SwitchbotCommand(String command) {

        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
