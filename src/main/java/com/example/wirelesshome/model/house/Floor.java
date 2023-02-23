package com.example.wirelesshome.model.house;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
public class Floor {
    @Id
    private String name;
    private int floorLevel;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Room> rooms;
}
