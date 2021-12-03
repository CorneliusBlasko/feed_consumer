package com.sparta.services.interfaces;

import com.sparta.models.LoadBatch;

public interface ICommandService {

  int saveBatch(LoadBatch loadBatch,String provider);
  LoadBatch convert(byte[] content);

}
