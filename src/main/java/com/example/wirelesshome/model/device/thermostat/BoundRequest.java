package com.example.wirelesshome.model.device.thermostat;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class BoundRequest {
    private String deviceId;
    private boolean enabled;
    private float highBound;
    private float lowBound;
}
