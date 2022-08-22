package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.DeviceStateMissing;
import com.example.wirelesshome.exception.HeatingMatNotFound;
import com.example.wirelesshome.model.device.DeviceManufacturer;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.mat.HeatingMat;
import com.example.wirelesshome.model.device.mat.HeatingMatStateRequest;
import com.example.wirelesshome.repository.HeatingMatRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.opentest4j.TestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
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

        String requestBody = mapper.writeValueAsString(heatingMat);
        String responseBody = mapper.writeValueAsString(heatingMat);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/mat")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));

        assertThat(repo.findAll()).isEqualTo(List.of(heatingMat));
        assertThat(repo.findByName("BR mat 1").orElseThrow(TestAbortedException::new)).isEqualTo(heatingMat);
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
    public void getHeatingMatMissing() throws Exception {
        String name = "BR mat 1";

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/mat/" + name)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(HeatingMatNotFound.class));
    }

    @Test
    void update() throws Exception {
        String name = "BR mat 1";
        HeatingMat mat = new HeatingMat(name, DeviceManufacturer.NEXAN);
        HeatingMat mat2 = new HeatingMat(name, DeviceManufacturer.NEXAN);

        repo.save(mat);

        mat2.setTemperature(20L);

        String requestBody = mapper.writeValueAsString(mat2);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/mat/")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(requestBody));

        assertThat(repo.findAll()).doesNotContain(mat);
        assertThat(repo.findAll()).contains(mat2);
    }

    @Test
    void updateState() throws Exception {
        HeatingMat heatingMat = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);
        String name = heatingMat.getName();
        HeatingMatStateRequest request = new HeatingMatStateRequest(40L, DeviceState.ON, 10);

        repo.save(heatingMat);

        LocalDateTime localDateTime = LocalDateTime.now().plus(request.getTimer(), ChronoUnit.MINUTES);

        when(schedulerService.schedule(heatingMat.getName(), "mat", request.getTimer())).thenReturn(localDateTime);

        heatingMat.setState(DeviceState.ON);
        heatingMat.setTemperature(40L);
        heatingMat.setShutOff(localDateTime);

        String responseBody = mapper.writeValueAsString(heatingMat);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/mat/" + name)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(schedulerService, times(1)).schedule(heatingMat.getName(), "mat", request.getTimer());
    }

    @Test
    void updateWithoutTimer() throws Exception {
        HeatingMat heatingMat = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);
        String name = heatingMat.getName();
        HeatingMatStateRequest request = new HeatingMatStateRequest(40L, DeviceState.ON, 0);

        repo.save(heatingMat);

        LocalDateTime localDateTime = LocalDateTime.now().plus(request.getTimer(), ChronoUnit.MINUTES);

        when(schedulerService.schedule(heatingMat.getName(), "mat", request.getTimer())).thenReturn(localDateTime);

        heatingMat.setState(DeviceState.ON);
        heatingMat.setTemperature(40L);
        heatingMat.setShutOff(null);

        String responseBody = mapper.writeValueAsString(heatingMat);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/mat/" + name)
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(schedulerService, times(1)).cancel(heatingMat.getName());
    }

    @Test
    void updateStateMissing() throws Exception {
        HeatingMat heatingMat = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);
        String name = heatingMat.getName();
        HeatingMatStateRequest request = new HeatingMatStateRequest(null, null, 0);

        repo.save(heatingMat);

        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/mat/" + name)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(DeviceStateMissing.class));
    }

    @Test
    void updateOff() throws Exception {
        HeatingMat heatingMat = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);
        String name = heatingMat.getName();
        HeatingMatStateRequest request = new HeatingMatStateRequest(null, DeviceState.OFF, 0);

        repo.save(heatingMat);

        heatingMat.setState(DeviceState.OFF);
        heatingMat.setTemperature(0L);
        heatingMat.setShutOff(null);

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
    void updateDefaultTemp() throws Exception {
        HeatingMat heatingMat = new HeatingMat("BR mat 1", DeviceManufacturer.NEXAN);
        String name = heatingMat.getName();
        HeatingMatStateRequest request = new HeatingMatStateRequest(null, DeviceState.ON, 1);

        repo.save(heatingMat);

        LocalDateTime localDateTime = LocalDateTime.now().plus(request.getTimer(), ChronoUnit.MINUTES);

        when(schedulerService.schedule(heatingMat.getName(), "mat", request.getTimer())).thenReturn(localDateTime);


        heatingMat.setState(DeviceState.ON);
        heatingMat.setTemperature(20L);
        heatingMat.setShutOff(localDateTime);

        String responseBody = mapper.writeValueAsString(heatingMat);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/mat/" + name)
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

        verify(schedulerService, times(1))
                .schedule(heatingMat.getName(), "mat", request.getTimer());
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