package com.example.projetremb.entity;

import jakarta.persistence.*;

@Entity
public class Traitement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codeBarre;
    private Boolean existe;
    private String nomMedicament;
    private String typeMedicament;
    private Double prixMedicament;
    // Getters and setters


    public Long getId() {
        return id;
    }

    public String getCodeBarre() {
        return codeBarre;
    }

    public Boolean getExiste() {
        return existe;
    }

    public String getNomMedicament() {
        return nomMedicament;
    }

    public String getTypeMedicament() {
        return typeMedicament;
    }

    public Double getPrixMedicament() {
        return prixMedicament;
    }
}
