package com.example.wirelesshome.model.house;

import com.example.wirelesshome.model.device.light.Light;
import com.example.wirelesshome.model.device.mat.HeatingMat;
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
public class Room {
    @Id
    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<HeatingMat> heatingMats;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Light> lights;
}
