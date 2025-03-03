package com.example.springbatchlog.calculator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

@Component
public class ItemCalculator {
    private static final Logger logger = LoggerFactory.getLogger(ItemCalculator.class);

    public void processItems(int start, int end, StepContribution contribution, ChunkContext chunkContext) {
        String jobName = chunkContext.getStepContext().getJobName();
        try {
            MDC.put("jobName", jobName);
            for (int i = start; i <= end; i++) {
                logger.info("Processing item {}", i);
            }
        } finally {
            MDC.remove("jobName");
        }
    }
} 