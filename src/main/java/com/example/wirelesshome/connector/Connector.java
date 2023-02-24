package com.example.wirelesshome.connector;

public interface Connector {

    Object getDevice(String deviceId);

    Boolean update(String deviceId, Object device);

    Boolean commands(String deviceId, Object request);
}
