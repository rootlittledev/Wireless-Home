package com.example.wirelesshome.model.device.switchbot;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SwitchBotStatus {
    private String statusCode;
    private Object body;
}
