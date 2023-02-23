package com.example.wirelesshome.repository;

import com.example.wirelesshome.model.house.Owner;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OwnerRepo extends CrudRepository<Owner, UUID> {

    @Override
    @NonNull
    List<Owner> findAll();

    Optional<Owner> findByName(String name);
}
