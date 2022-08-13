package com.example.wirelesshome.model.device.mat;

import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.util.LocalDateTimeDeserializer;
import com.example.wirelesshome.util.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime shutOff = LocalDateTime.now();

    public HeatingMat(String name, DeviceManufacturer manufacturer) {
        this.name = name;
        this.manufacturer = manufacturer;
    }

}
