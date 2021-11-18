package com.sparta.models;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class LoadBatch{
    long numberOfRecords;
    List<Record> records;
}
