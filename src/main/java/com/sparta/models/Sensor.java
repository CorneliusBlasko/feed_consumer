package com.sparta.models;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Sensor{
    String id;
    int measure;
}