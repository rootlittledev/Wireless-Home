package com.example.wirelesshome.controller;

import com.example.wirelesshome.model.device.light.Light;
import com.example.wirelesshome.model.device.light.LightStateRequest;
import com.example.wirelesshome.service.LightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/light")
public class LightController {

    private final LightService service;

    public LightController(LightService service) {

        this.service = service;
    }

    @GetMapping
    public List<Light> getLights() {
        log.info("Get all Lights");

        return service.getAll();
    }

    @GetMapping("/{name}")
    public Light getLight(@PathVariable String name) {
        log.info("Get Light with name {}", name);

        return service.getLight(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Light addLight(@RequestBody Light light) {
        log.info("Save new light {}", light);

        return service.save(light);
    }

    @PutMapping
    public Light updateLight(@RequestBody Light light) {
        log.info("Update light {}, with {}", light.getName(), light);

        return service.update(light);
    }

    @PutMapping("/{name}")
    public Light updateLight(@PathVariable String name, @RequestBody LightStateRequest state) {
        log.info("Update light {}, with state {}", name, state);

        return service.update(name, state);
    }

    @DeleteMapping("/{name}")
    public void deleteLight(@PathVariable String name) {
        log.info("Delete light {}", name);

        service.delete(name);
    }
}
