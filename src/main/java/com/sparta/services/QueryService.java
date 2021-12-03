package com.sparta.services;

import com.sparta.repositories.interfaces.IRepository;
import com.sparta.repositories.MapRepository;
import com.sparta.services.interfaces.IQueryService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class QueryService implements IQueryService {


  private final IRepository repository;

  public QueryService(ApplicationContext context){
    this.repository = context.getBean(MapRepository.class);
  }

  @Override
  public int findByProvider(String provider) {
    return this.repository.findByProvider(provider);
  }
}
