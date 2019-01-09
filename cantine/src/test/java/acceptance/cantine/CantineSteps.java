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

    private boolean              alertInscriptionImpossibleGenerated = false;

    public CantineSteps( AuthenticationGateway authenticationGateway, EleveRepository eleveRepository,
            CantineRepository cantineRepository, EnseignantRepository enseignantRepository ) {
        this.eleveRepository = eleveRepository;
        this.enseignantRepository = enseignantRepository;
        this.serviceCantine = new ServiceCantine( authenticationGateway, cantineRepository );
    }

    @Quand( "je {string} tente d inscrire l'�l�ve {string}" )
    public void je_tente_d_inscrire_l_�l�ve( String nomEnseignant, String nomEleve ) {
        // r�cup�ration de l'�l�ve
        Optional<Eleve> optionalEleve = eleveRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEleve ) ).findFirst();
        // r�cup�ration de l'enseignant
        Optional<Enseignant> optionalEnseignant = enseignantRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEnseignant ) ).findFirst();
        if ( optionalEleve.isPresent() && optionalEnseignant.isPresent() ) {
            // inscription
            try {
                serviceCantine.enregisterPresence( optionalEnseignant.get(), optionalEleve.get() );
                alertInscriptionImpossibleGenerated = false;
            } catch ( EnseignantNonAutentifieException e ) {
                alertInscriptionImpossibleGenerated = true;
            }
        }
    }

    @Alors( "{string} est inscrit" )
    public void est_inscrit( String nomEleve ) {
        // r�cup�ration de l'�l�ve
        Optional<Eleve> optionalEleve = eleveRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEleve ) ).findFirst();
        // verification inscription
        try {
            assertTrue( serviceCantine.getCantineDuJour().contains( optionalEleve.get() ) );
        } catch ( NoSuchElementException e ) {
            fail( "El�ve non trouv�" );
        }
    }

    @Soit( "l �l�ve {string} n'est pas inscrit" )
    public void l_�l�ve_n_est_pas_inscrit( String nomEleve ) {
        Optional<Eleve> optionalEleve = eleveRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEleve ) ).findFirst();
        assertFalse( serviceCantine.getCantineDuJour().contains( optionalEleve.get() ) );
    }

    @Alors( "une alerte pour identification de l enseignant impossible" )
    public void une_alerte_pour_identification_de_l_enseignant_impossible() {
        assertTrue( alertInscriptionImpossibleGenerated );
    }
}
