package com.example.wirelesshome.service;

import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.mat.HeatingMat;
import com.example.wirelesshome.model.device.mat.HeatingMatStateRequest;
import com.example.wirelesshome.repository.HeatingMatRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    @MockBean
    public SchedulerService schedulerService;

    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    void cleanUp() {
        repo.deleteAll();
    }

    @Test
    void getHeatingMats() throws Exception {
        HeatingMat heatingMat1 = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);
        HeatingMat heatingMat2 = new HeatingMat("BR mat 2", DeviceManufacturer.NEXAN);

        List<HeatingMat> heatingMats = List.of(heatingMat1, heatingMat2);

        repo.saveAll(heatingMats);

        String responseBody = mapper.writeValueAsString(heatingMats);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/mat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    void save() throws Exception {
        HeatingMat heatingMat = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);

        String requestBody =  mapper.writeValueAsString(heatingMat);
        String responseBody = mapper.writeValueAsString(heatingMat);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/mat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));

        assertThat(repo.findAll()).isEqualTo(List.of(heatingMat));
        //assertThat(repo.findByName("BR mat 1"));

    }

    @Test
    void getHeatingMat() throws Exception {
        String name = "BR mat 1";
        HeatingMat heatingMat = new HeatingMat(name, DeviceManufacturer.NEXAN);

        String responseBody = mapper.writeValueAsString(heatingMat);

        repo.save(heatingMat);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/mat/" + name)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

    }

    @Test
    void updateHeatingMat() throws Exception {
        HeatingMat heatingMat = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);
        String name = heatingMat.getName();
        HeatingMatStateRequest request = new HeatingMatStateRequest(null, DeviceState.ON, 0);

        repo.save(heatingMat);

        heatingMat.setState(DeviceState.ON);
        heatingMat.setTemperature(20L);
        heatingMat.setShutOff(LocalDateTime.MIN);

        String responseBody = mapper.writeValueAsString(heatingMat);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/mat/" + name)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

    }

    @Test
    void delete() throws Exception {
        HeatingMat heatingMat1 = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);
        HeatingMat heatingMat2 = new HeatingMat("BR mat 2", DeviceManufacturer.NEXAN);
        HeatingMat heatingMat3 = new HeatingMat("BR mat 3", DeviceManufacturer.NEXAN);
        HeatingMat heatingMat4 = new HeatingMat("BR mat 4", DeviceManufacturer.NEXAN);

        String name = heatingMat1.getName();
        repo.saveAll(List.of(heatingMat1, heatingMat2, heatingMat3, heatingMat4));


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/mat/{name}/", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(repo.findAll()).doesNotContain(heatingMat1);

    }
}