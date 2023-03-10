package com.example.wirelesshome.model.device.thermostat;

import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.thermometer.Thermometer;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Thermostat {

    @Id
    private String id;

    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Thermometer thermometer;

    private DeviceState state = DeviceState.OFF;

    private float desiredTemperature;

    private float highBound = 0.0f;
    private float lowBound = 0.0f;

    private boolean isBound = false;
    private FanSpeed fanSpeed;
    private ThermostatMode mode;
}
