package com.sparta.repositories;

import com.sparta.models.Record;
import com.sparta.repositories.interfaces.IRepository;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class MapRepository implements IRepository {

    private final MultiValuedMap<String,Record> mapRepository;

    public MapRepository(){
        this.mapRepository = new ArrayListValuedHashMap<>();
    }

    /**
     * Saves the record for the selected provider.
     *
     * @param record   The Record to be persisted
     * @param provider The provider to whom this record belong
     * @return The Record that's been persisted
     */
    @Override
    public Boolean saveRecord(Record record,String provider){
        return this.mapRepository.put(provider,record);
    }

    /**
     * Finds all the Records for the selected provider.
     *
     * @param provider The provider to whom the Records belong
     * @return The amount of Records belonging to this provider
     */
    @Override
    public int findByProvider(String provider){
        return this.mapRepository.get(provider).size();
    }
}
