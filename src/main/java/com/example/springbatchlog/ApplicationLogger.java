package com.example.springbatchlog;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class ApplicationLogger {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationLogger.class);
    private static final String LOG_FILE = "logs/application.log";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void logToFile(String message) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            String timestamp = LocalDateTime.now().format(formatter);
            writer.println(String.format("[%s] %s", timestamp, message));
            logger.info("Logged to file: {}", message);
        } catch (IOException e) {
            logger.error("Error writing to log file: {}", e.getMessage());
        }
    }

    public void logApplicationStart() {
        logToFile("Application started");
    }

    public void logApplicationEnd() {
        logToFile("Application ended");
    }

    public void logJobStart(String jobName) {
        logToFile(String.format("Job %s started", jobName));
    }

    public void logJobEnd(String jobName) {
        logToFile(String.format("Job %s completed", jobName));
    }

    public void logError(String jobName, Exception e) {
        logToFile(String.format("Error in job %s: %s", jobName, e.getMessage()));
    }
} 