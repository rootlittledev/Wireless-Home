package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.OwnerNotFound;
import com.example.wirelesshome.model.house.Room;
import com.example.wirelesshome.repository.RoomRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RoomService {

    private final RoomRepo repo;

    public RoomService(RoomRepo repo) {
        this.repo = repo;
    }

    public List<Room> getAll() {
        return repo.findAll();
    }

    public Room save(Room room) {
        return repo.save(room);
    }

    public Room update(Room room) {
        return repo.save(room);
    }

    public Room getRoom(String name) {
        return repo.findByName(name)
                .orElseThrow(() -> new OwnerNotFound(name));
    }

}
