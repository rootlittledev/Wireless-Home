package com.example.wirelesshome.model.device.mat;

import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.DeviceState;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSS")
    private LocalDateTime shutOff;

    public HeatingMat(String name, DeviceManufacturer manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }

}
