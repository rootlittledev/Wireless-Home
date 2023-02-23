package com.example.wirelesshome.controller;

import com.example.wirelesshome.model.house.Floor;
import com.example.wirelesshome.service.FloorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/floor")
public class FloorController {

    private final FloorService service;

    public FloorController(FloorService service) {

        this.service = service;
    }

    @GetMapping
    public List<Floor> GetFloors() {
        log.info("Get all Floors");

        return service.getAll();
    }

    @GetMapping("/{name}")
    public Floor getFloor(@PathVariable String name) {
        log.info("Find Floor with name {}", name);

        return service.getFloor(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Floor addFloor(@RequestBody Floor floor) {
        log.info("Save new floor {}", floor);

        return service.save(floor);
    }

    @PutMapping
    public Floor update(@RequestBody Floor floor) {
        log.info("Update floor {}, with {}", floor.getName(), floor);

        return service.update(floor);
    }

}
