package com.sparta.mappers;

import com.sparta.models.LoadBatch;
import com.sparta.testutils.Utils;
import org.junit.jupiter.api.*;

import java.io.IOException;

public class ByteArrayToLoadBatchMapperTest{

    private ByteArrayToLoadBatchMapper mapper;

    @BeforeEach
    public void setUp(){
        this.mapper = new ByteArrayToLoadBatchMapper();
    }

    @AfterEach
    public void clean(){
        this.mapper = null;
    }

    @Test
    @DisplayName("Sanity test")
    public void test_00(){
        Assertions.assertNotNull(this.mapper);
    }

    @Test
    @DisplayName("Mapper test")
    public void test_01() throws IOException{

        byte[] byteArray = Utils.convertTestFileToByteArray();
        LoadBatch loadBatch = this.mapper.convert(byteArray);

        Assertions.assertNotNull(loadBatch);
        Assertions.assertEquals("Meereen",loadBatch.getRecords().get(1).getCity());
    }

}
