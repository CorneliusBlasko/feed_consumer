package com.sparta.repositories;

import com.sparta.mappers.ByteArrayToLoadBatchMapper;
import com.sparta.models.LoadBatch;
import com.sparta.services.MainService;
import com.sparta.testutils.Utils;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

public class MainServiceTest{

    private MainService service;
    private ByteArrayToLoadBatchMapper mapper;
    private LoadBatch loadBatch;

    @BeforeEach
    public void setUp() throws IOException{
        //Objects
        MapRepository repository = new MapRepository();
        this.mapper = new ByteArrayToLoadBatchMapper();
        byte[] byteArray = Utils.convertTestFileToByteArray();
        this.loadBatch = this.mapper.convert(byteArray);

        //Mocks
        ApplicationContext contextMock = Mockito.mock(ApplicationContext.class);
        Mockito.doReturn(repository).when(contextMock).getBean(MapRepository.class);
        Mockito.doReturn(this.mapper).when(contextMock).getBean(ByteArrayToLoadBatchMapper.class);

        //Service
        this.service = new MainService(contextMock);
    }

    @AfterEach
    public void clean(){
        this.service = null;
        this.mapper = null;
        this.loadBatch = null;
    }

    @Test
    @DisplayName("Sanity test")
    public void test_00(){
        Assertions.assertNotNull(this.service);
        Assertions.assertNotNull(this.mapper);
    }

    @Test
    @DisplayName("Repository load test")
    public void test_01(){
        Assertions.assertEquals(360,this.service.save(this.loadBatch,"MockProvider"));
    }

    @Test
    @DisplayName("Repository save test")
    public void test_02(){
        this.service.save(this.loadBatch,"MockProvider");
        Assertions.assertEquals(360,this.service.findByProvider("MockProvider"));
    }
}
