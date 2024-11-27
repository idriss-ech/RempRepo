package com.example.projetremb.repository;

import com.example.projetremb.entity.Dossier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DossierRepository extends JpaRepository<Dossier, Long> {
}

