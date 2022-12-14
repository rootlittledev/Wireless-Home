package com.example.wirelesshome.controller;

import com.example.wirelesshome.model.device.mat.HeatingMat;
import com.example.wirelesshome.model.device.mat.HeatingMatStateRequest;
import com.example.wirelesshome.service.HeatingMatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/mat")
public class HeatingMatController {

    private final HeatingMatService service;

    public HeatingMatController(HeatingMatService service) {

        this.service = service;
    }

    @GetMapping
    public List<HeatingMat> getHeatingMats() {
        log.info("Get all Heating Mats");
        return service.getAll();
    }

    @GetMapping("/{name}")
    public HeatingMat getHeatingMat(@PathVariable String name) {
        log.info("Get Heating Mats with name {}", name);

        return service.getHeatingMat(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HeatingMat addHeatingMat(@RequestBody HeatingMat heatingMat) {
        log.info("Save new Heating Mats {}", heatingMat);

        return service.save(heatingMat);
    }

    @PutMapping
    public HeatingMat update(@RequestBody HeatingMat mat) {
        log.info("Update map {}, with {}", mat.getName(), mat);

        return service.update(mat);

    }

    @PutMapping("/{name}")
    public HeatingMat updateHeatingMat(@PathVariable String name, @RequestBody HeatingMatStateRequest state) {
        log.info("Update mat {}, with state {}", name, state);

        return service.update(name, state);

    }

    @PutMapping("/disable")
    public void disableMat(@RequestBody String name) {
        log.info("Timer run out, disabling mat: {}", name);

        service.disable(name);
    }

    @DeleteMapping("/{name}")
    public void deleteHeatingMat(@PathVariable String name) {
        log.info("Delete heating mat {}", name);

        service.delete(name);
    }

}
