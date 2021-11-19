package com.sparta.mappers;

import com.sparta.models.LoadBatch;
import com.sparta.models.Record;
import com.sparta.models.Sensor;
import com.sparta.models.SensorCollection;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class ByteArrayToLoadBatchMapper{

    public LoadBatch convert(byte[] input){
        List<Record> records = new ArrayList<>();

        ByteBuffer buffer = ByteBuffer.wrap(input);
        long numberOfRecords = buffer.getLong();

        for(int i = 0; i < numberOfRecords; i++){
            Record record = getRecord(buffer);
            records.add(record);
        }

        return LoadBatch.builder().numberOfRecords(numberOfRecords).records(records).build();
    }

    /**
     * Converts a ByteBuffer into a Record.
     *
     * @param recordData The ByteBuffer that contains the Record information
     * @return A Record with all the byte array information necessary
     */
    private Record getRecord(ByteBuffer recordData){
        long recordIndex = recordData.getLong();
        long timestamp = recordData.getLong();
        String city = getString(recordData);
        int numberBytesSensorData = recordData.getInt();
        SensorCollection sensorsData = getSensorCollection(recordData);
        long crc32SensorsData = recordData.getLong();

        return Record.builder().recordIndex(recordIndex).timestamp(timestamp).city(city)
                     .numberBytesSensorData(numberBytesSensorData).sensorsData(sensorsData)
                     .crc32SensorsData(crc32SensorsData).build();
    }

    /**
     * Converts a ByteBuffer into a Sensor collection.
     *
     * @param sensorData The ByteBuffer that contains the SensorCollection information
     * @return A SensorCollection with all the byte array information necessary
     */
    private SensorCollection getSensorCollection(ByteBuffer sensorData){
        int numberOfSensors = sensorData.getInt();
        List<Sensor> sensors = new ArrayList<>();

        for(int i = 0; i < numberOfSensors; i++){
            Sensor sensor = getSensor(sensorData);
            sensors.add(sensor);
        }

        return SensorCollection.builder().numberOfSensors(numberOfSensors).sensors(sensors).build();
    }

    /**
     * Converts a ByteBuffer into a Sensor.
     *
     * @param sensorData The ByteBuffer that contains the Sensor information
     * @return A Sensor with all the array information necessary
     */
    public Sensor getSensor(ByteBuffer sensorData){
        return Sensor.builder().id(getString(sensorData)).measure(sensorData.getInt()).build();
    }

    /**
     * Creates a String based on the next number of bytes (represented by ByteBuffer.getInt()) length
     *
     * @param stringData The byte array that contains the String information
     * @return A String with all the array information necessary
     */
    private String getString(ByteBuffer stringData){
        int numberOfBytes = stringData.getInt();
        byte[] dst = new byte[numberOfBytes];
        stringData.get(dst, 0, numberOfBytes);
        ByteBuffer buffer = ByteBuffer.wrap(dst);
        return StandardCharsets.UTF_8.decode(buffer).toString();
    }

}
