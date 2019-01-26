package acceptance.cantine;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.spechwsa.cantine.domain.Eleve;
import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.EnseignantNonAutentifieException;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;
import com.spechwsa.cantine.domain.repositories.CantineRepository;
import com.spechwsa.cantine.domain.repositories.EleveRepository;
import com.spechwsa.cantine.domain.repositories.EnseignantRepository;
import com.spechwsa.cantine.domain.services.ServiceCantine;

import cucumber.api.java.fr.Alors;
import cucumber.api.java.fr.Quand;
import cucumber.api.java.fr.Soit;

public class CantineSteps {
    private EleveRepository      eleveRepository;
    private EnseignantRepository enseignantRepository;

    private ServiceCantine       serviceCantine;

    private boolean              alertIdentificationImpossibleGenerated = false;

    public CantineSteps( AuthenticationGateway authenticationGateway, EleveRepository eleveRepository,
            CantineRepository cantineRepository, EnseignantRepository enseignantRepository ) {
        this.eleveRepository = eleveRepository;
        this.enseignantRepository = enseignantRepository;
        this.serviceCantine = new ServiceCantine( authenticationGateway, cantineRepository );
    }

    @Quand( "je {string} tente d inscrire l'élève {string}" )
    public void je_tente_d_inscrire_l_élève( String nomEnseignant, String nomEleve ) {
        // récupération de l'éléve
        Optional<Eleve> optionalEleve = eleveRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEleve ) ).findFirst();
        // récupération de l'enseignant
        Optional<Enseignant> optionalEnseignant = enseignantRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEnseignant ) ).findFirst();
        if ( optionalEleve.isPresent() && optionalEnseignant.isPresent() ) {
            // inscription
            try {
                serviceCantine.enregisterPresence( optionalEnseignant.get(), optionalEleve.get() );
                alertIdentificationImpossibleGenerated = false;
            } catch ( EnseignantNonAutentifieException e ) {
                alertIdentificationImpossibleGenerated = true;
            }
        }
    }

    @Alors( "{string} est inscrit" )
    public void est_inscrit( String nomEleve ) {
        // récupération de l'éléve
        Optional<Eleve> optionalEleve = eleveRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEleve ) ).findFirst();
        // verification inscription
        try {
            assertTrue( serviceCantine.getCantineDuJour().contains( optionalEleve.get() ) );
            assertTrue(serviceCantine.estEnregistreAujourdhui(optionalEleve.get()));
        } catch ( NoSuchElementException e ) {
            fail( "Eléve non trouvé" );
        }
    }

    @Soit( "l éléve {string} n'est pas inscrit" )
    public void l_éléve_n_est_pas_inscrit( String nomEleve ) {
        Optional<Eleve> optionalEleve = eleveRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEleve ) ).findFirst();
        assertFalse( serviceCantine.getCantineDuJour().contains( optionalEleve.get() ) );
    }

    @Alors( "une alerte pour identification de l enseignant impossible" )
    public void une_alerte_pour_identification_de_l_enseignant_impossible() {
        assertTrue( alertIdentificationImpossibleGenerated );
    }
    
    @Quand("je {string} tente de désinscrire l'élève {string}")
    public void je_tente_d_déssinscrire_l_élève(String nomEnseignant, String nomEleve) {
        // récupération de l'éléve
        Optional<Eleve> optionalEleve = eleveRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEleve ) ).findFirst();
        // récupération de l'enseignant
        Optional<Enseignant> optionalEnseignant = enseignantRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEnseignant ) ).findFirst();
        if ( optionalEleve.isPresent() && optionalEnseignant.isPresent() ) {
            // désinscription
            try {
                serviceCantine.deEnregisterPresence( optionalEnseignant.get(), optionalEleve.get() );
                alertIdentificationImpossibleGenerated = false;
            } catch ( EnseignantNonAutentifieException e ) {
                alertIdentificationImpossibleGenerated = true;
            }
        }
    }
}
