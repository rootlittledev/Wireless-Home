package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.OwnerNotFound;
import com.example.wirelesshome.model.house.Owner;
import com.example.wirelesshome.repository.OwnerRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OwnerService {

    private final OwnerRepo repo;

    public OwnerService(OwnerRepo repo) {
        this.repo = repo;
    }

    public List<Owner> getAll() {
        return repo.findAll();
    }

    public Owner save(Owner owner) {
        return repo.save(owner);
    }

    public Owner update(Owner owner) {
        return repo.save(owner);
    }

    public Owner getOwner(String name) {
        return repo.findByName(name)
                .orElseThrow(() -> new OwnerNotFound(name));
    }


  /*  public Owner updateInfo(String name, OwnerInfo info) {
        Owner owner = getOwner(name);
        String OwnersName = info.getName();
        String OwnersSurname = info.getSurname();
        String OwnersEmail = info.getEmail();
        String OwnersPhone = info.getPhone();


    }*/
}
