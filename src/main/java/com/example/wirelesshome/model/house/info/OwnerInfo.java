package com.example.wirelesshome.model.house.info;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OwnerInfo {

    private String name;
    private String surname;
    private String email;
    private String phone;
}
