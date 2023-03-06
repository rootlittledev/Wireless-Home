package com.example.wirelesshome.repository;

import com.example.wirelesshome.model.device.thermostat.Thermostat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ThermostatRepo extends CrudRepository<Thermostat, String> {
    @Override
    @NonNull
    List<Thermostat> findAll();

    Optional<Thermostat> findByName(String name);

    @NonNull
    Optional<Thermostat> findById(@NonNull String id);
}
