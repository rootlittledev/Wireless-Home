package com.example.wirelesshome.repository;

import com.example.wirelesshome.model.house.House;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface HouseRepo extends CrudRepository<House, String> {

    @Override
    @NonNull
    List<House> findAll();

    Optional<House> findByName(String name);
}
