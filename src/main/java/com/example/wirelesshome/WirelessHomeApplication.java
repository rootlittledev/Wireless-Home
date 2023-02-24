package com.example.wirelesshome;

import com.tuya.connector.spring.annotations.ConnectorScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConnectorScan(basePackages = "com.example.wirelesshome.connector.tuya")
@EnableConfigurationProperties
public class WirelessHomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WirelessHomeApplication.class, args);
    }

}
