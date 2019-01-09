package acceptance.cantine;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.repositories.EnseignantRepository;

import cucumber.api.java.fr.Soit;

public class EnseignantSteps {
    /*
     * DI par constructeur pour ne pas polluer code avec du langage framework.
     * On aurait aussi pu mettre @Autowired sur EnseignantRepository et
     * supprimer le constructeur
     */
    private EnseignantRepository enseignantRepository;

    public EnseignantSteps( EnseignantRepository enseignantRepository ) {
        this.enseignantRepository = enseignantRepository;
    }

    @Soit( "des enseignants existent:" )
    public void des_enseignants_existent( io.cucumber.datatable.DataTable dataTable ) {
        List<Map<String, String>> dataMaps = dataTable.asMaps( String.class, String.class );
        dataMaps.forEach( dataMap -> {
            Enseignant enseignant = new Enseignant( dataMap.get( "id" ), dataMap.get( "fisrtName" ),
                    dataMap.get( "lastName" ) );
            enseignantRepository.add( enseignant );
            assertTrue( enseignantRepository.getAll().contains( enseignant ) );
        } );
    }
}
