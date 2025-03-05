package com.example.springbatchlog.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AnotherService {
    private static final Logger logger = LoggerFactory.getLogger(AnotherService.class);

    public void doSomething(int start, int end) {
        // This log will include the job context from MDC
        logger.info("Doing something with item {} to {}", start, end);
    }
} 