package com.sparta.services;

import com.sparta.mappers.ByteArrayToLoadBatchMapper;
import com.sparta.models.LoadBatch;
import com.sparta.models.Record;
import com.sparta.repositories.interfaces.IRepository;
import com.sparta.repositories.MapRepository;
import com.sparta.services.interfaces.ICommandService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CommandService implements ICommandService {

  private final IRepository repository;
  private final ByteArrayToLoadBatchMapper mapper;

  public CommandService(ApplicationContext context){
    this.mapper = context.getBean(ByteArrayToLoadBatchMapper.class);
    this.repository = context.getBean(MapRepository.class);
  }

  @Override
  public int saveBatch(LoadBatch loadBatch, String provider) {
    int providerRecords = 0;

    for(Record record : loadBatch.getRecords()){
      if(!this.repository.saveRecord(record,provider)){
        throw new RuntimeException("Record could not be saved.");
      }
      providerRecords++;
    }

    return providerRecords;

  }

  @Override
  public LoadBatch convert(byte[] content) {
    return this.mapper.convert(content);
  }
}
