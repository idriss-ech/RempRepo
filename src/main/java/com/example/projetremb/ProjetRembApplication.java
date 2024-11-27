package com.example.projetremb;

import org.springframework.batch.core.JobParameters;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;


@SpringBootApplication
public class ProjetRembApplication implements CommandLineRunner {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importDossierJob;

    public static void main(String[] args) {
        SpringApplication.run(ProjetRembApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        jobLauncher.run(importDossierJob, new JobParameters());
    }
}

