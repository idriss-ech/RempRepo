package com.example.projetremb.processor;

import com.example.projetremb.entity.Dossier;
import com.example.projetremb.entity.Traitement;

import com.example.projetremb.entity.MedicamentReferentiel;
import com.example.projetremb.repository.MedicamentReferentielRepository;
import org.springframework.batch.item.ItemProcessor;

public class CalculProcessor implements ItemProcessor<Dossier, Dossier> {

    private final MedicamentReferentielRepository medicamentReferentielRepository;

    public CalculProcessor(MedicamentReferentielRepository medicamentReferentielRepository) {
        this.medicamentReferentielRepository = medicamentReferentielRepository;
    }

    @Override
    public Dossier process(Dossier dossier) throws Exception {
        double remboursementConsultation = dossier.getPrixConsultation() * 0.7; // Appliquer un pourcentage fixe de 70%
        double remboursementTraitements = dossier.getTraitements().stream()
                .filter(Traitement::getExiste)
                .mapToDouble(traitement -> {
                    MedicamentReferentiel referentiel = medicamentReferentielRepository.findByCode(traitement.getCodeBarre());
                    if (referentiel != null) {
                        double taux = Double.parseDouble(referentiel.getTauxRemboursement().replace("%", "")) / 100;
                        return referentiel.getPpv() * taux;
                    }
                    return 0;
                })
                .sum();

        dossier.setMontantRemboursement(remboursementConsultation + remboursementTraitements); // Utiliser le nouveau champ
        return dossier;
    }
}

