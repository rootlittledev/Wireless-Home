package com.example.wirelesshome.service;

import com.example.wirelesshome.exception.DeviceStateMissing;
import com.example.wirelesshome.exception.LightNotFound;
import com.example.wirelesshome.model.device.*;
import com.example.wirelesshome.model.device.light.Light;
import com.example.wirelesshome.model.device.light.LightColor;
import com.example.wirelesshome.model.device.light.LightStateRequest;
import com.example.wirelesshome.repository.LightRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LightServiceTest {

    @Autowired
    public LightService service;

    @Autowired
    public LightRepo repo;

    @Autowired
    public ObjectMapper mapper;

    @Autowired
    public MockMvc mockMvc;

    @BeforeEach
    void cleanUp() {
        repo.deleteAll();
    }

    @Test
    void getAll() throws Exception {
        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        Light light2 = new Light("Light LR 2", DeviceManufacturer.XIAOMI);

        List<Light> lights = List.of(light1, light2);

        repo.saveAll(lights);

        String responseBody = mapper.writeValueAsString(lights);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/light")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

    }

    @Test
    void save() throws Exception {
        Light light = new Light("Light LR 1", DeviceManufacturer.XIAOMI);

        String requestBody = "{ \"name\" : \"Light LR 1\", \"manufacturer\": \"XIAOMI\" }";
        String responseBody = mapper.writeValueAsString(light);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/api/light")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseBody));

        assertThat(repo.findAll()).isEqualTo(List.of(light));
    }

    @Test
    void getLight() throws Exception {
        String name = "Light LR 1";
        Light light = new Light(name, DeviceManufacturer.XIAOMI);

        String responseBody = mapper.writeValueAsString(light);

        repo.save(light);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/light/" + name)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));
    }

    @Test
    public void lightMissing() throws Exception {
        String name = "Light LR 1";

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/api/light/" + name).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(LightNotFound.class));
    }

    @Test
    void updateMissingName() throws Exception{

        String name = "Light LR 3";

        LightStateRequest request = new LightStateRequest(null, null, null);

        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/light/" + name + "/state")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(LightNotFound.class));


    }

    @Test
    void updateMissingState() throws Exception{

        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        String name = light1.getName();
        LightStateRequest request = new LightStateRequest(null, null, null);

        repo.save(light1);

        String requestBody = mapper.writeValueAsString(request);


        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/api/light/" + name + "/state")
                                .content(requestBody)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(DeviceStateMissing.class));

    }

    @Test
    void update() throws Exception {
        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        String name = light1.getName();
        LightStateRequest request = new LightStateRequest(null, DeviceState.ON, null);

        repo.save(light1);

        light1.setState(DeviceState.ON);
        light1.setBrightness(100L);
        light1.setColor(LightColor.WHITE);

        String responseBody = mapper.writeValueAsString(light1);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/light/" + name + "/state")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

    }

    @Test
    void updateOffZeroBrightness() throws Exception {
        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        String name = light1.getName();
        LightStateRequest request = new LightStateRequest(null, DeviceState.OFF, null);

        repo.save(light1);

        light1.setState(DeviceState.OFF);
        light1.setBrightness(0L);
        light1.setColor(LightColor.WHITE);

        String responseBody = mapper.writeValueAsString(light1);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/light/" + name + "/state")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

    }

    @Test
    void updateDefaultBrightness() throws Exception {
        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        String name = light1.getName();
        LightStateRequest request = new LightStateRequest(100L, DeviceState.ON, null);

        repo.save(light1);

        light1.setState(DeviceState.ON);
        light1.setBrightness(100L);
        light1.setColor(LightColor.WHITE);

        String responseBody = mapper.writeValueAsString(light1);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/light/" + name + "/state")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

    }

    @Test
    void updateDefaultColor() throws Exception{

        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        String name = light1.getName();
        LightStateRequest request = new LightStateRequest(null, DeviceState.ON, null);

        repo.save(light1);

        light1.setState(DeviceState.ON);
        light1.setBrightness(100L);
        light1.setColor(LightColor.WHITE);

        String responseBody = mapper.writeValueAsString(light1);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/light/" + name + "/state")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

    }

    @Test
    void updateBrightness() throws Exception{

        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        String name = light1.getName();
        LightStateRequest request = new LightStateRequest(60L, DeviceState.ON, null);

        repo.save(light1);

        light1.setState(DeviceState.ON);
        light1.setBrightness(60L);
        light1.setColor(LightColor.WHITE);

        String responseBody = mapper.writeValueAsString(light1);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/light/" + name + "/state")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));


    }

    @Test
    void updateColor() throws Exception{

        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        String name = light1.getName();
        LightStateRequest request = new LightStateRequest(null, DeviceState.ON, LightColor.YELLOW);

        repo.save(light1);

        light1.setState(DeviceState.ON);
        light1.setBrightness(100L);
        light1.setColor(LightColor.YELLOW);

        String responseBody = mapper.writeValueAsString(light1);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/light/" + name + "/state")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));


    }


    @Test
    void brightnessOnOffState() throws Exception {
        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        String name = light1.getName();
        LightStateRequest request = new LightStateRequest(20L, DeviceState.OFF, null);

        repo.save(light1);

        light1.setState(DeviceState.OFF);
        light1.setBrightness(0L);
        light1.setColor(LightColor.WHITE);

        String responseBody = mapper.writeValueAsString(light1);
        String requestBody = mapper.writeValueAsString(request);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/light/" + name + "/state")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseBody));

    }

    @Test
    void deleteLight() throws Exception {
        Light light1 = new Light("Light LR 1", DeviceManufacturer.XIAOMI);
        Light light2 = new Light("Light LR 2", DeviceManufacturer.XIAOMI);
        Light light3 = new Light("Light LR 3", DeviceManufacturer.XIAOMI);
        Light light4 = new Light("Light LR 4", DeviceManufacturer.XIAOMI);

        String name = light1.getName();
        repo.saveAll(List.of(light1, light2, light3, light4));


        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/light/{name}/", name )
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        assertThat(repo.findAll()).doesNotContain(light1);
    }





}
