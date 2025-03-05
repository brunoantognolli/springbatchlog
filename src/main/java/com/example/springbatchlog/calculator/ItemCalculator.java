package com.example.springbatchlog.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;
import com.example.springbatchlog.logging.JobLogging;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.springbatchlog.service.AnotherService;

@Component
public class ItemCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ItemCalculator.class);

    @Autowired
    private AnotherService anotherService;

    @JobLogging("Processing batch of items BFA")
    public void processItems(int start, int end, StepContribution contribution, ChunkContext chunkContext) {
        logger.info("Starting processing items from {} to {}", start, end);
        
        for (int i = start; i <= end; i++) {
            logger.info("Processing item {}", i);
        }

        // This method will inherit the MDC context
        anotherService.doSomething(start, end);

        
        logger.info("Completed processing items from {} to {}", start, end);
    }
}