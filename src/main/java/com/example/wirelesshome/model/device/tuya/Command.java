package com.example.wirelesshome.model.device.tuya;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Command {

    private String code;
    private Object value;

}
