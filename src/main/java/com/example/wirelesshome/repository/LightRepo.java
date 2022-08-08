package com.example.wirelesshome.repository;

import com.example.wirelesshome.model.device.light.Light;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LightRepo extends CrudRepository<Light, String> {
    @Override
    List<Light> findAll();

    Optional<Light> findByName(String name);
}
