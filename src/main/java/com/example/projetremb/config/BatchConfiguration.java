package com.example.projetremb.config;

import com.example.projetremb.entity.Dossier;
import com.example.projetremb.listener.CustomSkipListener;
import com.example.projetremb.listener.JobCompletionNotificationListener;
import com.example.projetremb.listener.StepCompletionNotificationListener;
import com.example.projetremb.processor.CalculProcessor;
import com.example.projetremb.processor.ValidationProcessor;
import com.example.projetremb.repository.MedicamentReferentielRepository;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Arrays;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BatchConfiguration.class);

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final EntityManagerFactory entityManagerFactory;
    private final MedicamentReferentielRepository medicamentReferentielRepository;

    public BatchConfiguration(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              EntityManagerFactory entityManagerFactory,
                              MedicamentReferentielRepository medicamentReferentielRepository) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.entityManagerFactory = entityManagerFactory;
        this.medicamentReferentielRepository = medicamentReferentielRepository;
    }

    @Bean
    public JsonItemReader<Dossier> reader() {
        logger.info("Initializing JSON Item Reader");
        return new JsonItemReaderBuilder<Dossier>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Dossier.class))
                .resource(new ClassPathResource("dossiers.json"))
                .name("dossierJsonItemReader")
                .build();
    }

    @Bean
    public ValidationProcessor validationProcessor() {
        return new ValidationProcessor();
    }

    @Bean
    public CalculProcessor calculProcessor() {
        return new CalculProcessor(medicamentReferentielRepository);
    }

    @Bean
    public CompositeItemProcessor<Dossier, Dossier> processor() {
        logger.info("Initializing Composite Processor");
        return new CompositeItemProcessorBuilder<Dossier, Dossier>()
                .delegates(Arrays.asList(validationProcessor(), calculProcessor()))
                .build();
    }

    @Bean
    public JpaItemWriter<Dossier> writer() {
        logger.info("Initializing JPA Item Writer");
        return new JpaItemWriterBuilder<Dossier>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step step1(StepCompletionNotificationListener stepListener, CustomSkipListener skipListener) {
        return new StepBuilder("step1", jobRepository)
                .<Dossier, Dossier>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .listener(stepListener)
                .listener(skipListener)
                .faultTolerant()
                .retry(Exception.class)
                .noRetry(ValidationException.class)
                .retryLimit(3)
                .skip(ValidationException.class)
                .skipLimit(10)
                .build();
    }

    @Bean
    public Job importDossierJob(JobCompletionNotificationListener jobListener, Step step1) {
        logger.info("Initializing Job");
        return new JobBuilder("importDossierJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(jobListener) // Listener pour notifier la fin du Job
                .start(step1)
                .build();
    }
}
