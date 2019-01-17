package com.spechwsa.cantine.configuration;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;

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
 * Pour que ce composant s'execute Mettre la propriété cantine.db.init à true
 * dans le fichier application.properties le fichier xml de creation des éléves
 * à été réalisé depuis http://www.generatedata.com/
 **/

@Component
@ConditionalOnProperty( name = "cantine.db.init", havingValue = "true" )
public class DdInit implements CommandLineRunner {

    private static final String TAG_NOM         = "nom";

    private static final String TAG_PRENOM      = "prenom";

    private static final String TAG_ID          = "id";

    private static final String TAG_ELEVE       = "eleve";

    private static final String LIST_ELEVES_XML_FILE = "listEleves.xml";

    @Autowired
    EleveRepository             eleveRepository;

    @Autowired
    EnseignantRepository        enseignantRepository;

    @Override
    public void run( String... args ) throws Exception {
        // TODO Auto-generated method stub
        System.out.println( "Init base de test" );
        initFakeInMemoryRepo();
        System.out.println( "base de test initialisée" );
    }

    private void initFakeInMemoryRepo() {
        initFakeElevesRepo();
        enseignantRepository.add( new Enseignant( "a", "Françoise", "MULLER" ) );
        enseignantRepository.add( new Enseignant( "addd", "Paul", "DUPUIS" ) );
    }

    private void initFakeElevesRepo() {
        InputStream inputStream = null;
        String id = "";
        String prenom = "";
        String nom = "";

        try {
            // de https://www.baeldung.com/reading-file-in-java
            ClassLoader classLoader = getClass().getClassLoader();
            inputStream = classLoader.getResourceAsStream( LIST_ELEVES_XML_FILE );

            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader( inputStream );

            // lecture du fichier XML
            while ( eventReader.hasNext() ) {
                XMLEvent event = eventReader.nextEvent();

                if ( event.isStartElement() ) {
                    if ( event.asStartElement().getName().getLocalPart().equals( TAG_ELEVE ) ) {
                        continue;
                    }
                }

                if ( event.isStartElement() ) {
                    if ( event.asStartElement().getName().getLocalPart().equals( TAG_ID ) ) {
                        event = eventReader.nextEvent();
                        id = event.asCharacters().getData();
                        continue;
                    }
                }
                if ( event.isStartElement() ) {
                    if ( event.asStartElement().getName().getLocalPart().equals( TAG_PRENOM ) ) {
                        event = eventReader.nextEvent();
                        prenom = event.asCharacters().getData();
                        continue;
                    }
                }
                if ( event.isStartElement() ) {
                    if ( event.asStartElement().getName().getLocalPart().equals( TAG_NOM ) ) {
                        event = eventReader.nextEvent();
                        nom = event.asCharacters().getData();
                        continue;
                    }
                }
                // si fin de l'element élève on l'ajoute dans la base
                if ( event.isEndElement() ) {
                    EndElement endElement = event.asEndElement();
                    if ( endElement.getName().getLocalPart().equals( TAG_ELEVE ) ) {
                        eleveRepository.add( new Eleve( id, prenom, nom ) );
                    }
                }

            }

        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            if ( inputStream != null ) {
                try {
                    inputStream.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
            }
        }

    }
}
