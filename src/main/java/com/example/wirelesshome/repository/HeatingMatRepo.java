package com.example.wirelesshome.repository;

import com.example.wirelesshome.model.device.mat.HeatingMat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface HeatingMatRepo extends CrudRepository<HeatingMat, String> {
    @Override
    @NonNull
    List<HeatingMat> findAll();

    Optional<HeatingMat> findByName(String name);
}
