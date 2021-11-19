package com.sparta.models;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SensorCollection{
    int numberOfSensors;
    List<Sensor> sensors;
}