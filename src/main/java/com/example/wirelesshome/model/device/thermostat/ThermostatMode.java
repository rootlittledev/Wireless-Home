package com.example.wirelesshome.model.device.thermostat;

public enum ThermostatMode {
    AUTO(1), COOLING(2), DRY(3), FAN(4), HEATING(5);

    private final int mode;

    ThermostatMode(int mode) {
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }
}
