package com.example.wirelesshome.model.device.tuya;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class TuyaDevice {
    List<Command> result;
}