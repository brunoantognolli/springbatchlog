package com.example.springbatchlog;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class SpringBatchLogApplication {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job1;

    @Autowired
    private Job job2;

    @Autowired
    private ApplicationLogger applicationLogger;

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchLogApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            applicationLogger.logApplicationStart();

            // Create unique job parameters for each run
            JobParameters jobParameters1 = new JobParametersBuilder()
                    .addDate("date", new Date())
                    .addString("jobName", "job1")
                    .toJobParameters();

            JobParameters jobParameters2 = new JobParametersBuilder()
                    .addDate("date", new Date())
                    .addString("jobName", "job2")
                    .toJobParameters();

            // Launch jobs in parallel using CompletableFuture
            CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
                try {
                    applicationLogger.logJobStart("job1");
                    jobLauncher.run(job1, jobParameters1);
                    applicationLogger.logJobEnd("job1");
                } catch (Exception e) {
                    applicationLogger.logError("job1", e);
                }
            });

            CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
                try {
                    applicationLogger.logJobStart("job2");
                    jobLauncher.run(job2, jobParameters2);
                    applicationLogger.logJobEnd("job2");
                } catch (Exception e) {
                    applicationLogger.logError("job2", e);
                }
            });

            // Wait for both jobs to complete
            CompletableFuture.allOf(future1, future2).join();
            
            applicationLogger.logApplicationEnd();
        };
    }
} 