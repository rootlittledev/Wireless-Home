package com.example.wirelesshome.model.device.mat;

import com.example.wirelesshome.model.device.DeviceState;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class HeatingMatStateRequest {
    private Long temperature;
    private DeviceState state;
    private long timer;
}
