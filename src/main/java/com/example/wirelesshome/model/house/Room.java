package com.example.wirelesshome.model.house;

import com.example.wirelesshome.model.device.light.Light;
import com.example.wirelesshome.model.device.mat.HeatingMat;
import com.example.wirelesshome.model.device.thermostat.Thermostat;
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
public class Room {
    @Id
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<HeatingMat> heatingMats;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Light> lights;

    @OneToOne(cascade = CascadeType.ALL)
    private Thermostat thermostat;
}
