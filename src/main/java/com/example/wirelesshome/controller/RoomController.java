package com.example.wirelesshome.controller;

import com.example.wirelesshome.model.house.Room;
import com.example.wirelesshome.service.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/room")
public class RoomController {

    private final RoomService service;

    public RoomController(RoomService service) {

        this.service = service;
    }

    @GetMapping
    public List<Room> GetRooms() {
        log.info("Get all Rooms");

        return service.getAll();
    }

    @GetMapping("/{name}")
    public Room getRoom(@PathVariable String name) {
        log.info("Find Room with name {}", name);

        return service.getRoom(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Room addRoom(@RequestBody Room room) {
        log.info("Save new room {}", room);

        return service.save(room);
    }

    @PutMapping
    public Room update(@RequestBody Room room) {
        log.info("Update room {}, with {}", room.getName(), room);

        return service.update(room);
    }

}
