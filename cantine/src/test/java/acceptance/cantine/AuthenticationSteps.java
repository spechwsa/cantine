package acceptance.cantine;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;
import com.spechwsa.cantine.domain.repositories.EnseignantRepository;

import cucumber.api.java.fr.Soit;

public class AuthenticationSteps {
    private AuthenticationGateway authenticationGateway;
    private EnseignantRepository  enseignantRepository;

    public AuthenticationSteps( AuthenticationGateway authenticationGateway,
            EnseignantRepository enseignantRepository ) {
        this.authenticationGateway = authenticationGateway;
        this.enseignantRepository = enseignantRepository;
    }

    @Soit( "je suis authentifié en tant que {string}" )
    public void je_suis_authentifié_en_tant_que( String nomEnseignant ) {

        // utilisation des stream :
        // https://openclassrooms.com/fr/courses/26832-apprenez-a-programmer-en-java/5013326-manipulez-vos-donnees-avec-les-streams
        Optional<Enseignant> optionalEnseignant = enseignantRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEnseignant ) ).findFirst();
        //
        // Java 8 Method Reference: How to Use it
        // https://www.codementor.io/eh3rrera/using-java-8-method-reference-du10866vx
        //

        // si il existe un enseignant lastName on procède à l'authentification
        optionalEnseignant.ifPresent( authenticationGateway::authenticate );

        // on vérifie que l'enseignant LastName est identifié.
        assertTrue( authenticationGateway.currentEnseignant().isPresent() );

    }

    @Soit( "je {string} n'est pas authentifié" )
    public void je_n_est_pas_authentifié( String nomEnseignant ) {
        Optional<Enseignant> optionalEnseignant = enseignantRepository.getAll().stream()
                .filter( c -> c.getLastName().equals( nomEnseignant ) ).findFirst();

        authenticationGateway.UnAuthenticate( optionalEnseignant.get() );

        try {
            if ( authenticationGateway.currentEnseignant().get().getLastName().equals( nomEnseignant ) )
                fail( "L'enseignant Authentifié :" + authenticationGateway.currentEnseignant().get().getLastName()
                        + " ne devrait pas l'être" );
        } catch ( NoSuchElementException e ) {
            // Okay pas d'enseignant authentifié
        }

    }

}
