package com.sparta.controllers;

import com.sparta.models.LoadBatch;
import com.sparta.services.CommandService;
import com.sparta.services.QueryService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class MainController{

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private final CommandService commandService;
    private final QueryService queryService;

    public MainController(ApplicationContext context){
        this.commandService = context.getBean(CommandService.class);
        this.queryService = context.getBean(QueryService.class);
    }

    /**
     * This endpoint saves a provider's request in the persistence layer. The provider request is composed by the
     * provider name and a list of Records.
     *
     * @param provider The provider sending the request
     * @param content  A byte array representation of the provider's Records
     * @return The total amount of Records processed for this request
     * @throws IOException An IOException can be thrown if the process goes wrong
     */
    @PostMapping("/load/{provider}")
    public int load(@PathVariable("provider") String provider,@RequestBody byte[] content) throws IOException{

        //Map the contents of the byte array into a LoadBatch model
        LoadBatch loadBatch = this.commandService.convert(content);

        logger.debug("Now loading " + loadBatch.getRecords().size() + " records for provider " + provider);

        //Save the model into the database and return the total amount of records persisted
        return this.commandService.saveBatch(loadBatch,provider);
    }

    /**
     * This endpoint counts all the Records existing for this provider and returns the total.
     *
     * @param provider The provider that must be looked for
     * @return The total amount of Records registered for this provider
     */
    @GetMapping("/data/{provider}/total")
    public int total(@PathVariable("provider") String provider){
        logger.debug("Querying records for provider " + provider);
        return this.queryService.findByProvider(provider);
    }

}
