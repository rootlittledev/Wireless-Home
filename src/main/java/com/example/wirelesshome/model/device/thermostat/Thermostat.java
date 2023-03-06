package com.example.wirelesshome.model.device.thermostat;

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

    private float desiredTemperature;
    private FanSpeed fanSpeed;
    private ThermostatMode mode;
}
