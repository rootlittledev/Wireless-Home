package com.example.wirelesshome.model.device.thermometer;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ThermometerStateRequest {

    private float temperature;
    private int humidity;
}
