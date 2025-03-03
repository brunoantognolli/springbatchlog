package com.example.springbatchlog.job1;

import com.example.springbatchlog.calculator.ItemCalculator;
import com.example.springbatchlog.listener.JobLoggingListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class Job1Configuration {
    
    private static final Logger logger = LoggerFactory.getLogger(Job1Configuration.class);
    private static final String JOB_NAME = "Job1";

    @Autowired
    private ItemCalculator itemCalculator;

    @Autowired
    private JobLoggingListener jobLoggingListener;

    @Bean
    public Job job1(JobRepository jobRepository, Step job1Step) {
        return new JobBuilder(JOB_NAME, jobRepository)
                .listener(jobLoggingListener)
                .start(job1Step)
                .build();
    }

    @Bean
    public Step job1Step(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("job1Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    try {
                        MDC.put("jobName", JOB_NAME);
                        logger.info("Starting calculations");
                        itemCalculator.processItems(1, 10, contribution, chunkContext);
                        logger.info("Finished calculations");
                        return RepeatStatus.FINISHED;
                    } finally {
                        MDC.remove("jobName");
                    }
                }, transactionManager)
                .build();
    }
} 