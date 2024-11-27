package com.example.projetremb.listener;

import com.example.projetremb.entity.SkipLog;
import com.example.projetremb.repository.SkipLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StepCompletionNotificationListener implements StepExecutionListener {

    private static final Logger log = LoggerFactory.getLogger(StepCompletionNotificationListener.class);

    @Autowired
    private SkipLogRepository skipLogRepository;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        log.info("Step started: {}", stepExecution.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        // Log success or failure of the step
        log.info("Step {} completed with status: {}", stepExecution.getStepName(), stepExecution.getExitStatus());

        // Log skipped items during the step
        Long skippedItems = stepExecution.getSkipCount();
        if (skippedItems > 0) {
            log.info("Skipped {} items", skippedItems);
            // Enregistrement des éléments ignorés
            for (int i = 0; i < skippedItems; i++) {
                SkipLog skipLog = new SkipLog();
                skipLog.setItemType("Process");
                skipLog.setReason("Item skipped during processing");
                skipLogRepository.save(skipLog); // Enregistrer chaque élément ignoré
            }
        }

        return stepExecution.getExitStatus();
    }
}
