package com.example.projetremb.entity;

import jakarta.persistence.*;

@Entity
public class MedicamentReferentiel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code; // Le code-barre du médicament
    private String nom; // Nom du médicament
    private String dci1; // DCI (Dénomination Commune Internationale) du médicament
    private String dosage1; // Dosage du médicament
    private String uniteDosage1; // Unité du dosage
    private String forme; // Forme du médicament
    private String presentation; // Présentation du médicament
    private Double ppv; // Prix de vente public
    private Double ph; // Prix hors-taxe
    private Double prixBr; // Prix brut
    private String princepsGenerique; // Indique si c'est un princeps ou un générique
    private String tauxRemboursement; // Taux de remboursement

    // Getters et setters


    public MedicamentReferentiel(String code, String nom, String dci1, String dosage1, String uniteDosage1, String forme, String presentation, Double ppv, Double ph, Double prixBr, String princepsGenerique, String tauxRemboursement) {
        this.code = code;
        this.nom = nom;
        this.dci1 = dci1;
        this.dosage1 = dosage1;
        this.uniteDosage1 = uniteDosage1;
        this.forme = forme;
        this.presentation = presentation;
        this.ppv = ppv;
        this.ph = ph;
        this.prixBr = prixBr;
        this.princepsGenerique = princepsGenerique;
        this.tauxRemboursement = tauxRemboursement;
    }

    public Double getPpv() {
        return ppv;
    }

    public String getTauxRemboursement() {
        return tauxRemboursement;
    }
}
