package com.sparta.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Record{

    long recordIndex;
    long timestamp;
    String city;
    int numberBytesSensorData;
    SensorCollection sensorsData;
    long crc32SensorsData;

}