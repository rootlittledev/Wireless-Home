package com.example.wirelesshome.service;

import com.example.wirelesshome.model.ScheduleRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
public class SchedulerService {

    @Value("${scheduler.url}")
    private String url;


    public LocalDateTime schedule(String name, String type, long timer) {
        ScheduleRequest request = new ScheduleRequest(name, type, timer);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<LocalDateTime> response
                = restTemplate.postForEntity(url, request, LocalDateTime.class);

        return response.getBody();
    }

    public void cancel(String name) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(url + name);
    }


}
