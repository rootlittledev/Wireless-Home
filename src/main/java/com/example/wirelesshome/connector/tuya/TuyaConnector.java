package com.example.wirelesshome.connector.tuya;

import com.example.wirelesshome.connector.Connector;
import com.tuya.connector.api.annotations.*;

public interface TuyaConnector extends Connector {

    @GET("/v1.0/iot-03/devices/{device_id}/status")
    Object getDevice(@Path("device_id") String deviceId);

    @PUT("/v1.0/iot-03/devices/{device_id}")
    Boolean update(@Path("device_id") String deviceId, @Body Object device);

    @POST("/v1.0/iot-03/devices/{device_id}/commands")
    Boolean commands(@Path("device_id") String deviceId, @Body Object request);
}