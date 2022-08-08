package com.example.wirelesshome.model.device.light;

import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.light.LightColor;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LightStateRequest {

    private Long brightness;
    private DeviceState state;
    private LightColor color;
}
