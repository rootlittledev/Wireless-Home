package com.example.wirelesshome.model.device.switchbot;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SwitchbotThermometer {
    private String deviceId;
    private String deviceType;
    private float temperature;
    private int humidity;

}
