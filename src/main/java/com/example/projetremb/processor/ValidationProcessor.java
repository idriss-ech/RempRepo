package com.example.projetremb.processor;

import com.example.projetremb.entity.Dossier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class ValidationProcessor implements ItemProcessor<Dossier, Dossier> {

    private static final Logger log = LoggerFactory.getLogger(ValidationProcessor.class);

    @Override
    public Dossier process(Dossier dossier) throws Exception {
        if (dossier.getNomAssure().isEmpty()) {
            log.warn("Nom de l'assuré est vide pour le dossier: {}", dossier);
            throw new ValidationException("Nom de l'assuré vide");
        }
        if (dossier.getNumeroAffiliation().isEmpty()) {
            log.warn("Numéro d'affiliation est vide pour le dossier: {}", dossier);
            throw new ValidationException("Numéro d'affiliation vide");
        }
        if (dossier.getPrixConsultation() <= 0) {
            log.warn("Prix de consultation invalide pour le dossier: {}", dossier);
            throw new ValidationException("Prix de consultation invalide");
        }
        if (dossier.getMontantTotalFrais() <= 0) {
            log.warn("Montant total des frais invalide pour le dossier: {}", dossier);
            throw new ValidationException("Montant total des frais invalide");
        }
        if (dossier.getTraitements().isEmpty()) {
            log.warn("Liste des traitements vide pour le dossier: {}", dossier);
            throw new ValidationException("Liste des traitements vide");
        }
        return dossier;
    }
}
