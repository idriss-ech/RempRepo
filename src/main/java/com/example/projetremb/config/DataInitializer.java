package com.example.projetremb.config;


import com.example.projetremb.entity.MedicamentReferentiel;
import com.example.projetremb.repository.MedicamentReferentielRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner initDatabase(MedicamentReferentielRepository repository) {
        return args -> {
            logger.info("Preloading data into MEDICAMENT_REFERENTIEL table");

            repository.save(new MedicamentReferentiel("6118001230068", "URO / EAU POUR IRRIGATION", "EAU POUR PREPARATION INJECTABLE", "3000", "ML", "SOLUTION POUR IRRIGATION", "1 POCHE 3 L", 95.00, 0.00, 95.00, "P", "0%"));
            repository.save(new MedicamentReferentiel("6118010116230", "ELOXATINE 5 MG/ML", "OXALIPLATINE", "200", "MG", "SOLUTION A DILUER POUR PERFUSION", "1 BOITE 1 FLACON 40 ML", 2882.00, 2555.00, 2882.00, "P", "70%"));
            repository.save(new MedicamentReferentiel("6118001081646", "ELOXATINE 5 MG/ML", "OXALIPLATINE", "200", "MG", "SOLUTION A DILUER POUR PERFUSION", "1 BOITE 1 FLACON 40 ML", 2882.00, 2555.00, 2882.00, "P", "70%"));
            repository.save(new MedicamentReferentiel("6118001182459", "VIVALAN", "VILOXAZINE", "100", "MG", "COMPRIME PELLICULE", "1 BOITE 20 COMPRIME", 60.70, 40.10, 60.70, "P", "0%"));
            repository.save(new MedicamentReferentiel("6118000120155", "ZADRYL", "CETIRIZINE", "1", "MG", "SOLUTION BUVABLE", "1 FLACON 60 ML", 31.90, 19.90, 31.90, "G", "70%"));
            repository.save(new MedicamentReferentiel("6118000241256", "MYNAZOL", "FLUCONAZOLE", "50", "MG", "GELULE", "1 BOITE 7 GELULE", 87.00, 54.20, 87.00, "G", "70%"));
            repository.save(new MedicamentReferentiel("6118000091974", "ALFACEFAL 125 MG/5 ML", "CEFACLOR", "1.5", "G", "POUDRE POUR SUSPENSION BUVABLE", "1 FLACON 60 ML", 45.00, 29.80, 45.00, "G", "0%"));
            repository.save(new MedicamentReferentiel("6118000041344", "NOCAND 50 MG", "FLUCONAZOLE", "50", "MG", "GELULE", "1 BOITE 7 GELULE", 35.00, 21.80, 35.00, "G", "70%"));
            repository.save(new MedicamentReferentiel("6118000100270", "MEDIATOR", "BENFLUOREX", "150", "MG", "COMPRIME ENROBE", "1 BOITE 30 COMPRIME", 60.00, 39.70, 60.00, "P", "0%"));
            repository.save(new MedicamentReferentiel("6118000071068", "GASTROLIBER", "LANSOPRAZOLE", "30", "MG", "GELULE GASTRO-RESISTANTE", "1 BOITE 15 GELULE GASTRO-RESISTANTE", 75.00, 46.70, 75.00, "G", "70%"));
            repository.save(new MedicamentReferentiel("6118000140870", "AGIDERM", "ACIDE FUSIDIQUE", "2", "%", "POMMADE", "1 TUBE 15 G", 25.00, 15.60, 25.00, "G", "70%"));

            logger.info("Data preloading complete.");
        };
    }
}

