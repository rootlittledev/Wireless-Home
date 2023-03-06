package com.example.wirelesshome.model.device.thermometer;

import com.example.wirelesshome.model.device.DeviceManufacturer;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Thermometer {
    @Id
    private String id;
    private String name;

    private DeviceManufacturer manufacturer;
    private float temperature = 0.0f;
    private int humidity = 0;

}
