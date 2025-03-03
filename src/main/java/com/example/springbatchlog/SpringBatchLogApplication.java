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

    public static void main(String[] args) {
        SpringApplication.run(SpringBatchLogApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
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
                    jobLauncher.run(job1, jobParameters1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
                try {
                    jobLauncher.run(job2, jobParameters2);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            // Wait for both jobs to complete
            CompletableFuture.allOf(future1, future2).join();
        };
    }
} 