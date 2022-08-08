package com.example.wirelesshome.model.device.light;

import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.DeviceState;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class Light {

    @Id
    private String name;

    private DeviceState state = DeviceState.OFF;

    private DeviceManufacturer manufacturer;

    private Long brightness = 0L;
    private LightColor color = LightColor.WHITE;

    public Light(String name, DeviceManufacturer manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }
}
