package com.example.wirelesshome.model.device.tuya;

public enum TuyaCommand {

    LIGHT("switch_led"), BRIGHTNESS("bright_value"), COLOR("colour_data"), WORK_MODE("work_mode");
    private final String command;

    TuyaCommand(String command){
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
