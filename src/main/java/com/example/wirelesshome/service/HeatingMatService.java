package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.HeatingMatNotFound;
import com.example.wirelesshome.exception.LightNotFound;
import com.example.wirelesshome.model.device.mat.HeatingMat;
import com.example.wirelesshome.repository.HeatingMatRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HeatingMatService {
    private final HeatingMatRepo repo;

    public HeatingMatService(HeatingMatRepo repo) {
        this.repo = repo;
    }

    public List<HeatingMat> getAll() {

        return repo.findAll();
    }

    public HeatingMat save(HeatingMat HeatingMat) {
        
        return repo.save(HeatingMat);
    }

    public HeatingMat getHeatingMat(String name) {
        return repo.findByName(name)
                .orElseThrow(() -> new HeatingMatNotFound(name));
    }

}
