package com.example.wirelesshome.controller;

import com.example.wirelesshome.model.house.Owner;
import com.example.wirelesshome.service.OwnerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/owner")
public class OwnerController {

    private final OwnerService service;

    public OwnerController(OwnerService service) {

        this.service = service;
    }

    @GetMapping
    public List<Owner> GetOwners() {
        log.info("Get all Owners");

        return service.getAll();
    }

    @GetMapping("/{name}")
    public Owner getOwner(@PathVariable String name) {
        log.info("Find Owner with name {}", name);

        return service.getOwner(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Owner addOwner(@RequestBody Owner owner) {
        log.info("Save new owner {}", owner);

        return service.save(owner);
    }

    @PutMapping
    public Owner update(@RequestBody Owner owner) {
        log.info("Update owner {}, with {}", owner.getName(), owner);

        return service.update(owner);
    }

    /*@PutMapping("/{name}")
    public Owner update(@PathVariable String name, @RequestBody OwnerInfo info) {
        log.info("Update owner {}, with info {}", name, info);

        return service.updateInfo(name,info);
    }*/
}
