package com.example.wirelesshome.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ScheduleRequest {
    private String name;

    private String type;
    private long timer;
}
