package com.example.wirelesshome.model.device.tuya;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TuyaCommandRequest {

    private List<Command> commands;

}
