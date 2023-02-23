package com.example.wirelesshome.model.house;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class House {
    @Id
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private Owner owner;

    private String address;

    private HouseType type;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Floor> floors;
}
