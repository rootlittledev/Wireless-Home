package com.example.wirelesshome.service;

import com.example.wirelesshome.repository.HeatingMatRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HeatingMatServiceTest {

    @Autowired
    public HeatingMatService service;

    @Autowired
    public HeatingMatRepo repo;

    @Autowired
    public ObjectMapper mapper;

    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    void cleanUp() {
        repo.deleteAll();
    }

    @Test
    void getAll() throws Exception{

//        String responseBody = mapper.writeValueAsString(mats);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                        .get("/api/mat")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(responseBody));
    }

    @Test
    void save() throws Exception{

    }

    @Test
    void getHeatingMat() throws Exception{

    }

    @Test
    void delete() throws Exception{



    }
}