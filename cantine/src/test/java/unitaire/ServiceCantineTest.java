package unitaire;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.spechwsa.cantine.adapters.InMemoryCantineRepository;
import com.spechwsa.cantine.domain.Eleve;
import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.EnseignantNonAutentifieException;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;
import com.spechwsa.cantine.domain.repositories.CantineRepository;
import com.spechwsa.cantine.domain.services.ServiceCantine;

public class ServiceCantineTest {
    @Mock
    AuthenticationGateway autentificationGateway;

    CantineRepository     cantineRepository;

    // Tells Mockito to create the mocks based on the @Mock annotation
    @Rule
    public MockitoRule    mockitoRule = MockitoJUnit.rule();

    @Test
    public void unEnseignantNonIdentifieNeDoitPasPouvoirInscrireUnEleve() {
        cantineRepository = new InMemoryCantineRepository();
        ServiceCantine serviceCantine = new ServiceCantine( autentificationGateway, cantineRepository );
        Eleve eleve = new Eleve( "abc", "Marc", "DUPUIS" );
        Enseignant enseignant = new Enseignant( "abc", "Paul", "DUPOND" );
        Enseignant enseignantNonidentifié = new Enseignant( "def", "Françoise", "MULLER" );

        when( autentificationGateway.currentEnseignant() ).thenReturn( Optional.of( enseignant ) );

        try {
            serviceCantine.enregisterPresence( enseignantNonidentifié, eleve );
            fail( "EnseignantNonAutentifie non levée alors que l'enseignant n'est pas authentifié" );
        } catch ( EnseignantNonAutentifieException e ) {
            assertFalse( serviceCantine.getCantineDuJour().stream().anyMatch( p -> p.equals( eleve ) ) );
        }

    }

    @Test
    public void unEnseignantIdentifieDoitPouvoirInscrireUnEleve() {
        cantineRepository = new InMemoryCantineRepository();
        ServiceCantine serviceCantine = new ServiceCantine( autentificationGateway, cantineRepository );
        Eleve eleve = new Eleve( "abc", "Marc", "DUPUIS" );
        Enseignant enseignant = new Enseignant( "abc", "Paul", "DUPOND" );

        when( autentificationGateway.currentEnseignant() ).thenReturn( Optional.of( enseignant ) );
        try {
            serviceCantine.enregisterPresence( enseignant, eleve );
            assertTrue( serviceCantine.getCantineDuJour().contains( eleve ) );
        } catch ( EnseignantNonAutentifieException e ) {
            fail( "EnseignantNonAutentifie levée alors que l'enseignant est authentifié" );
        }
    }
    
    @Test
    public void unEnseignantIdentifieDoitPouvoirDeInscrireUnEleve() {
        cantineRepository = new InMemoryCantineRepository();
        ServiceCantine serviceCantine = new ServiceCantine( autentificationGateway, cantineRepository );
        Eleve eleve = new Eleve( "abc", "Marc", "DUPUIS" );
        Enseignant enseignant = new Enseignant( "abc", "Paul", "DUPOND" );

        when( autentificationGateway.currentEnseignant() ).thenReturn( Optional.of( enseignant ) );
        try {
            serviceCantine.enregisterPresence( enseignant, eleve );
            assertTrue( serviceCantine.getCantineDuJour().contains( eleve ) );
            serviceCantine.deEnregisterPresence( enseignant, eleve );
            assertFalse( serviceCantine.getCantineDuJour().contains( eleve ) );
        } catch ( EnseignantNonAutentifieException e ) {
            fail( "EnseignantNonAutentifie levée alors que l'enseignant est authentifié" );
        }
        
    }
    
    @Test
    public void unEnseignantNonIdentifieNeDoitPasPouvoirDeInscrireUnEleve() {
        cantineRepository = new InMemoryCantineRepository();
        ServiceCantine serviceCantine = new ServiceCantine( autentificationGateway, cantineRepository );
        Eleve eleve = new Eleve( "abc", "Marc", "DUPUIS" );
        Enseignant enseignant = new Enseignant( "abc", "Paul", "DUPOND" );
        Enseignant enseignantNonidentifié = new Enseignant( "def", "Françoise", "MULLER" );

        when( autentificationGateway.currentEnseignant() ).thenReturn( Optional.of( enseignant ) );

        try {
            serviceCantine.enregisterPresence( enseignant, eleve );
            assertTrue( serviceCantine.getCantineDuJour().contains( eleve ) );
            serviceCantine.deEnregisterPresence( enseignantNonidentifié, eleve );
            //élève toujours enregistré
            assertTrue( serviceCantine.getCantineDuJour().stream().anyMatch( p -> p.equals( eleve ) ) );
            fail( "EnseignantNonAutentifie non levée alors que l'enseignant n'est pas authentifié" );
        } catch ( EnseignantNonAutentifieException e ) {
            // Exception et élève toujours enregistré
            assertTrue( serviceCantine.getCantineDuJour().stream().anyMatch( p -> p.equals( eleve ) ) );
        }

    }
}
