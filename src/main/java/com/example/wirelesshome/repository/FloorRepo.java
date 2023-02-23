package com.example.wirelesshome.repository;

import com.example.wirelesshome.model.house.Floor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface FloorRepo extends CrudRepository<Floor, String> {

    @Override
    @NonNull
    List<Floor> findAll();

    Optional<Floor> findByName(String name);
}
