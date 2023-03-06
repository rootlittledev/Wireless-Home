package com.example.wirelesshome.model.device.thermostat;

public enum FanSpeed {
    AUTO(1), LOW(2), MID(3), High(4);

    private final int speed;
    FanSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
