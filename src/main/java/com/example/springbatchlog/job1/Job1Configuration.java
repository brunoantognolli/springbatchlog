package com.example.springbatchlog.job1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class Job1Configuration {
    
    private static final Logger logger = LoggerFactory.getLogger(Job1Configuration.class);

    @Bean
    public Job job1(JobRepository jobRepository, Step job1Step) {
        return new JobBuilder("job1", jobRepository)
                .start(job1Step)
                .build();
    }

    @Bean
    public Step job1Step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("job1Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    for (int i = 0; i < 10; i++) {
                        logger.info("Job1 - Processing item {}", i);
                        Thread.sleep(1000); // Simulate work
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
} 