package com.spechwsa.cantine.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.spechwsa.cantine.domain.Eleve;
import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.repositories.EleveRepository;
import com.spechwsa.cantine.domain.repositories.EnseignantRepository;

/**
 * https://dzone.com/articles/spring-boot-applicationrunner-and-commandlinerunne
 * Pour que ce composant s'execute 
 * Mettre la propriété cantine.db.init à true dans le fichier application.properties
**/

@Component
@ConditionalOnProperty(name="cantine.db.init", havingValue="true")
public class DdInit implements CommandLineRunner {
    
    @Autowired
    EleveRepository eleveRepository;

    @Autowired
    EnseignantRepository enseignantRepository;
    
    @Override
    public void run( String... args ) throws Exception {
        // TODO Auto-generated method stub
        System.out.println( "Init base de test" );
        initFakeInMemoryRepo();
        System.out.println( "base de test initialisée" );
    }
    
    private void initFakeInMemoryRepo() {
        eleveRepository.add( new Eleve( "a", "François" , "THOMAS" ) );
        eleveRepository.add( new Eleve( "bfr", "Marc" , "MULLER" ) );
        enseignantRepository.add( new Enseignant( "a", "Françoise", "MULLER" ) );
        enseignantRepository.add( new Enseignant( "addd", "Paul", "DUPUIS" ) );
    }

}
