package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.OwnerNotFound;
import com.example.wirelesshome.model.house.House;
import com.example.wirelesshome.repository.HouseRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HouseService {

    private final HouseRepo repo;

    public HouseService(HouseRepo repo) {
        this.repo = repo;
    }

    public List<House> getAll() {
        return repo.findAll();
    }

    public House save(House house) {
        return repo.save(house);
    }

    public House update(House house) {
        return repo.save(house);
    }

    public House getHouse(String name) {
        return repo.findByName(name)
                .orElseThrow(() -> new OwnerNotFound(name));
    }

}
