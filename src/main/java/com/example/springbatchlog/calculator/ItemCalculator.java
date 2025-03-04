package com.example.springbatchlog.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;
import com.example.springbatchlog.logging.JobLogging;

@Component
public class ItemCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ItemCalculator.class);

    @JobLogging
    public void processItems(int start, int end, StepContribution contribution, ChunkContext chunkContext) {
        logger.info("Starting processing items from {} to {}", start, end);
        
        for (int i = start; i <= end; i++) {
            logger.info("Processing item {}", i);
        }
        
        logger.info("Completed processing items from {} to {}", start, end);
    }
} 