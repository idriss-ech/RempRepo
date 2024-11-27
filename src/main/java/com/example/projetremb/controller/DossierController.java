package com.example.projetremb.controller;

import com.example.projetremb.entity.Dossier;
import com.example.projetremb.repository.DossierRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/dossiers")
public class DossierController {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job importDossierJob;

    @Autowired
    private DossierRepository dossierRepository;

    @PostMapping("/startBatch")
    public ResponseEntity<String> startBatchJob() {
        try {
            jobLauncher.run(importDossierJob, new JobParametersBuilder()
                    .addDate("launchDate", new Date())
                    .toJobParameters());
            return ResponseEntity.ok("Batch job started successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error starting batch job: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Dossier>> getAllDossiers() {
        List<Dossier> dossiers = dossierRepository.findAll();
        return ResponseEntity.ok(dossiers);
    }
}
