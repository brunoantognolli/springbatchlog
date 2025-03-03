package com.example.springbatchlog;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.core.ConsoleAppender;
import ch.qos.logback.core.FileAppender;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import jakarta.annotation.PostConstruct;
import ch.qos.logback.classic.Logger;

@Configuration
public class LogbackConfig {

    @PostConstruct
    public void init() {
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        // Create and configure console appender
        ConsoleAppender consoleAppender = new ConsoleAppender();
        consoleAppender.setContext(context);
        consoleAppender.setName("Console");
        
        PatternLayoutEncoder consoleEncoder = new PatternLayoutEncoder();
        consoleEncoder.setContext(context);
        consoleEncoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        consoleAppender.setEncoder(consoleEncoder);
        consoleAppender.start();

        // Create and configure Job1 file appender
        FileAppender job1FileAppender = new FileAppender();
        job1FileAppender.setContext(context);
        job1FileAppender.setName("Job1File");
        job1FileAppender.setFile("logs/process1.log");
        
        PatternLayoutEncoder job1Encoder = new PatternLayoutEncoder();
        job1Encoder.setContext(context);
        job1Encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        job1FileAppender.setEncoder(job1Encoder);
        job1FileAppender.start();

        // Create and configure Job2 file appender
        FileAppender job2FileAppender = new FileAppender();
        job2FileAppender.setContext(context);
        job2FileAppender.setName("Job2File");
        job2FileAppender.setFile("logs/process2.log");
        
        PatternLayoutEncoder job2Encoder = new PatternLayoutEncoder();
        job2Encoder.setContext(context);
        job2Encoder.setPattern("%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n");
        job2FileAppender.setEncoder(job2Encoder);
        job2FileAppender.start();

        // Configure Job1 logger
        Logger job1Logger = context.getLogger("com.example.springbatchlog.job1");
        job1Logger.setAdditive(false);
        job1Logger.setLevel(ch.qos.logback.classic.Level.INFO);
        job1Logger.addAppender(job1FileAppender);
        job1Logger.addAppender(consoleAppender);

        // Configure Job2 logger
        Logger job2Logger = context.getLogger("com.example.springbatchlog.job2");
        job2Logger.setAdditive(false);
        job2Logger.setLevel(ch.qos.logback.classic.Level.INFO);
        job2Logger.addAppender(job2FileAppender);
        job2Logger.addAppender(consoleAppender);

        // Configure root logger
        Logger rootLogger = context.getLogger(Logger.ROOT_LOGGER_NAME);
        rootLogger.setLevel(ch.qos.logback.classic.Level.INFO);
        rootLogger.addAppender(consoleAppender);
    }
} 