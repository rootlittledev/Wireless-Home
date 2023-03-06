package com.example.wirelesshome.model.device.switchbot;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class SwitchbotCommandRequest {
    private String commandType;
    private String command;
    private Object parameter;


}
