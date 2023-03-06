package com.example.wirelesshome.connector.switchbot;

import com.example.wirelesshome.connector.Connector;
import com.example.wirelesshome.exception.DeviceStateMissing;
import com.example.wirelesshome.model.device.switchbot.SwitchBotStatus;
import com.example.wirelesshome.model.device.thermometer.Thermometer;
import com.example.wirelesshome.util.SignatureUtils;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class SwitchBotConnector implements Connector {

    @Value("${switch.bot.secret}")
    private String secret;

    @Value("${switch.bot.token}")
    private String token;

    @Value("${switch.bot.url}")
    private String url;

    private final ModelMapper mapper;

    private final RestTemplate client;

    public SwitchBotConnector(ModelMapper mapper, RestTemplate client) {
        this.mapper = mapper;
        this.client = client;
    }

    @PostConstruct
    private void init() {
       log.info("Devices: {}", getDevices());
    }

    @NotNull
    private Object getDevices() {
        String uri = "/v1.1/devices";

        HttpEntity<Void> requestEntity = getHttpEntity();


        return send(uri, requestEntity, String.class);
    }

    public Object getDevice(String deviceId) {
        String uri = "/v1.1/devices/" + deviceId + "/status";

        HttpEntity<Void> requestEntity = getHttpEntity();

        SwitchBotStatus body = Optional.ofNullable(send(uri, requestEntity, SwitchBotStatus.class).getBody()).orElseThrow(DeviceStateMissing::new);

        return mapper.map(body.getBody(), Thermometer.class);
    }

    @Override
    public Boolean update(String deviceId, Object device) {
        return null;
    }

    @Override
    public Boolean commands(String deviceId, Object request) {
        return null;
    }

    @NotNull
    private <T> ResponseEntity<T> send(String uri, HttpEntity<Void> requestEntity, Class<T> type) {

        return client.exchange(url + uri, HttpMethod.GET, requestEntity, type);
    }

    @NotNull
    private HttpEntity<Void> getHttpEntity() {
        String nonce = UUID.randomUUID().toString();
        String time = "" + Instant.now().toEpochMilli();
        String data = token + time + nonce;

        String signature = SignatureUtils.getSignature(secret, data);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("sign", signature);
        headers.set("nonce", nonce);
        headers.set("t", time);

        return new HttpEntity<>(headers);
    }
}
