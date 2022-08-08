package com.example.wirelesshome.model.device.mat;

import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.DeviceState;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class HeatingMat {
    @Id
    private String name;

    private DeviceState state = DeviceState.OFF;

    private DeviceManufacturer manufacturer;

    private Long temperature = 0L;

    private LocalDateTime shutOff = LocalDateTime.now();

    public HeatingMat(String name, DeviceManufacturer manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }

}
