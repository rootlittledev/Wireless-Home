package com.example.wirelesshome.model.device.thermostat;

import com.example.wirelesshome.model.device.DeviceState;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ThermostatStateRequest {

    private DeviceState state;
    private float desiredTemperature;

    private FanSpeed fanSpeed;

    private ThermostatMode mode;
}
