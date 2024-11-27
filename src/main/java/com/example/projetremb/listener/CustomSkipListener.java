package com.example.projetremb.listener;

import com.example.projetremb.entity.Dossier;
import com.example.projetremb.entity.SkipLog;
import com.example.projetremb.repository.SkipLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class CustomSkipListener implements SkipListener<Dossier, Dossier> {
    private static final Logger log = LoggerFactory.getLogger(CustomSkipListener.class);
    private final AtomicInteger itemCounter = new AtomicInteger(0);
    private final SkipLogRepository skipLogRepository;

    public CustomSkipListener(SkipLogRepository skipLogRepository) {
        this.skipLogRepository = skipLogRepository;
    }

    @Override
    public void onSkipInRead(Throwable t) {
        String itemId = "Unknown_Item_" + itemCounter.incrementAndGet();
        log.warn("Item skipped during reading: {}", t.getMessage());
        saveSkipLog(itemId, "Read", extractReason(t));
    }

    @Override
    public void onSkipInWrite(Dossier item, Throwable t) {
        String itemId = getItemId(item);
        log.warn("Item {} skipped during writing: {}", itemId, t.getMessage());
        saveSkipLog(itemId, "Write", extractReason(t));
    }

    @Override
    public void onSkipInProcess(Dossier item, Throwable t) {
        String itemId = getItemId(item);
        log.warn("Item {} skipped during processing: {}", itemId, t.getMessage());
        saveSkipLog(itemId, "Process", extractReason(t));
    }

    private String getItemId(Dossier item) {
        if (item == null) {
            return "Unknown_Item_" + itemCounter.incrementAndGet();
        }

        if (item.getNumeroAffiliation() != null && !item.getNumeroAffiliation().isEmpty()) {
            return item.getNumeroAffiliation();
        }

        if (item.getImmatriculation() != null && !item.getImmatriculation().isEmpty()) {
            return item.getImmatriculation();
        }

        return "Fallback_Item_" + itemCounter.incrementAndGet();
    }

    @Transactional
    public void saveSkipLog(String itemId, String itemType, String reason) {
        SkipLog skipLog = new SkipLog();
        skipLog.setItemId("23");
        skipLog.setItemType(itemType);
        skipLog.setReason(reason);

        skipLogRepository.save(skipLog);
    }

    private String extractReason(Throwable t) {
        if (t == null) {
            return "Unknown reason";
        }

        if (t instanceof ValidationException) {
            return t.getMessage();
        }

        return t.toString();
    }
}