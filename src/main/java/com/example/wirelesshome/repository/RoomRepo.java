package com.example.wirelesshome.repository;

import com.example.wirelesshome.model.house.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface RoomRepo extends CrudRepository<Room, String> {

    @Override
    @NonNull
    List<Room> findAll();

    Optional<Room> findByName(String name);
}
