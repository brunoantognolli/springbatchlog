package com.example.springbatchlog.job2;

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
public class Job2Configuration {
    
    private static final Logger logger = LoggerFactory.getLogger(Job2Configuration.class);

    @Bean
    public Job job2(JobRepository jobRepository, Step job2Step) {
        return new JobBuilder("job2", jobRepository)
                .start(job2Step)
                .build();
    }

    @Bean
    public Step job2Step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("job2Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    for (int i = 0; i < 5; i++) {
                        logger.info("Job2 - Processing item {}", i);
                        Thread.sleep(2000); // Simulate work with different duration
                    }
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }
} 