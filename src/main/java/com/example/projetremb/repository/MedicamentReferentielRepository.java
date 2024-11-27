package com.example.projetremb.repository;

import com.example.projetremb.entity.MedicamentReferentiel;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface MedicamentReferentielRepository extends JpaRepository<MedicamentReferentiel, Long> {
    MedicamentReferentiel findByCode(String code);
}
