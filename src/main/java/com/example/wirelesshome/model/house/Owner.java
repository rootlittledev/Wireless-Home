package com.example.wirelesshome.model.house;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Owner {
    @Id
    private String name;
    private String surname;
    private String email;
    private String phone;
}
