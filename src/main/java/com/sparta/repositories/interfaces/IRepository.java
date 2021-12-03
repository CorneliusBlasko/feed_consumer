package com.sparta.repositories.interfaces;

import com.sparta.models.Record;

public interface IRepository {

  Boolean saveRecord(Record record,String provider);

  int findByProvider(String provider);

}
