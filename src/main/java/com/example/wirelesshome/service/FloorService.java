package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.OwnerNotFound;
import com.example.wirelesshome.model.house.Floor;
import com.example.wirelesshome.repository.FloorRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FloorService {

    private final FloorRepo repo;

    public FloorService(FloorRepo repo) {
        this.repo = repo;
    }

    public List<Floor> getAll() {
        return repo.findAll();
    }

    public Floor save(Floor floor) {
        return repo.save(floor);
    }

    public Floor update(Floor floor) {
        return repo.save(floor);
    }

    public Floor getFloor(String name) {
        return repo.findByName(name)
                .orElseThrow(() -> new OwnerNotFound(name));
    }

}
