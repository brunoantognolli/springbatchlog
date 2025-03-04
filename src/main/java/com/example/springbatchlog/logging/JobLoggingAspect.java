package com.example.springbatchlog.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class JobLoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(JobLoggingAspect.class);

    @Around("@annotation(jobLogging)")
    public Object around(ProceedingJoinPoint joinPoint, JobLogging jobLogging) throws Throwable {
        Map<String, String> contextMap = new HashMap<>();
        
        try {
            // Extract job context information
            Object[] args = joinPoint.getArgs();
            for (Object arg : args) {
                if (arg instanceof ChunkContext) {
                    ChunkContext chunkContext = (ChunkContext) arg;
                    String jobName = chunkContext.getStepContext().getJobName();
                    String stepName = chunkContext.getStepContext().getStepName();
                    String threadName = Thread.currentThread().getName();

                    contextMap.put("jobName", jobName);
                    contextMap.put("stepName", stepName);
                    contextMap.put("threadName", threadName);

                    // Put all context values into MDC
                    contextMap.forEach(MDC::put);

                    // Use the custom message if provided
                    String message = jobLogging.value().isEmpty() ? 
                        "Job execution" : jobLogging.value();
                    logger.debug("{} - Job: {}, Step: {}, Thread: {}", 
                              message, jobName, stepName, threadName);
                    break;
                }
            }

            return joinPoint.proceed();
        } finally {
            // Clean up all MDC values we set
            contextMap.keySet().forEach(MDC::remove);
        }
    }
} 