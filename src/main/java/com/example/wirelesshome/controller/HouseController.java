package com.example.wirelesshome.controller;

import com.example.wirelesshome.model.house.House;
import com.example.wirelesshome.service.HouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/house")
public class HouseController {
    private final HouseService service;

    public HouseController(HouseService service) {

        this.service = service;
    }

    @GetMapping
    public List<House> GetHouses() {
        log.info("Get all Houses");

        return service.getAll();
    }

    @GetMapping("/{name}")
    public House getHouse(@PathVariable String name) {
        log.info("Find House with name {}", name);

        return service.getHouse(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public House addHouse(@RequestBody House house) {
        log.info("Save new house {}", house);

        return service.save(house);
    }

    @PutMapping
    public House update(@RequestBody House house) {
        log.info("Update house {}, with {}", house.getName(), house);

        return service.update(house);
    }

}
