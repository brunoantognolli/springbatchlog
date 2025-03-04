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
        String stepName = chunkContext.getStepContext().getStepName();
        String threadName = Thread.currentThread().getName();
        
        try {
            // Set context information for logging
            MDC.put("jobName", jobName);
            MDC.put("stepName", stepName);
            MDC.put("threadName", threadName);
            
            logger.info("Starting processing items from {} to {} in job: {}, step: {}, thread: {}", 
                       start, end, jobName, stepName, threadName);
            
            for (int i = start; i <= end; i++) {
                logger.info("Processing item {}", i);
            }
            
            logger.info("Completed processing items from {} to {}", start, end);
        } finally {
            // Clean up MDC context
            MDC.remove("jobName");
            MDC.remove("stepName");
            MDC.remove("threadName");
        }
    }
} 