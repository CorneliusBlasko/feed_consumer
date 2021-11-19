package com.sparta.services;

import com.sparta.mappers.ByteArrayToLoadBatchMapper;
import com.sparta.models.LoadBatch;
import com.sparta.models.Record;
import com.sparta.repositories.MapRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class MainService{

    private final ByteArrayToLoadBatchMapper mapper;
    private final MapRepository repository;

    public MainService(ApplicationContext context){
        this.mapper = context.getBean(ByteArrayToLoadBatchMapper.class);
        this.repository = context.getBean(MapRepository.class);
    }

    /**
     * This method calls the mapper that converts a byte array into a LoadBatch.
     *
     * @param content The byte array containing all the information
     * @return The LoadBatch model representing the byte[] information
     */
    public LoadBatch convert(byte[] content){
        return this.mapper.convert(content);
    }

    /**
     * Calls the repository and persists the information of the provider and its LoadBatch.
     *
     * @param loadBatch The LoadBatch to persist
     * @param provider  The provider to whom this LoadBatch belong
     * @return The amount of records in the LoadBatch that's been persisted
     */
    public int save(LoadBatch loadBatch,String provider){
        int providerRecords = 0;

        for(Record record : loadBatch.getRecords()){
            if(!this.repository.save(record,provider)){
                throw new RuntimeException("Record could not be saved.");
            }
            providerRecords++;
        }

        return providerRecords;
    }

    /**
     * Retrieves the total amount of records for the selected provider.
     *
     * @param provider The provider of the records
     * @return The total amount of records for this provider in the database
     */
    public int findByProvider(String provider){
        return this.repository.findByProvider(provider);
    }
}
