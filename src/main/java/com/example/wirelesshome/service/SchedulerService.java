package com.example.wirelesshome.service;

import com.example.wirelesshome.model.ScheduleRequest;
import com.example.wirelesshome.model.device.DeviceState;
import com.example.wirelesshome.model.device.mat.HeatingMat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;

@Service
public class SchedulerService {

    @Value("${scheduler.url}")
    private String url;


    public LocalDateTime schedule(String name, String type, int timer) {
        ScheduleRequest request = new ScheduleRequest(name, type, timer);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LocalDateTime> response
                = restTemplate.postForEntity(url, request, LocalDateTime.class);

        return response.getBody();
    }


}
