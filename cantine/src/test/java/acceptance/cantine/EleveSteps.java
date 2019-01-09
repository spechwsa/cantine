package acceptance.cantine;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import com.spechwsa.cantine.domain.Eleve;
import com.spechwsa.cantine.domain.repositories.EleveRepository;

import cucumber.api.java.fr.Soit;

public class EleveSteps {

    private EleveRepository eleveRepository;

    public EleveSteps( EleveRepository eleveRepository ) {
        this.eleveRepository = eleveRepository;
    }

    @Soit( "des eleves existent:" )
    public void des_eleves_existent( io.cucumber.datatable.DataTable dataTable ) {
        List<Map<String, String>> dataMaps = dataTable.asMaps( String.class, String.class );
        dataMaps.forEach( dataMap -> {
            Eleve eleve = new Eleve( dataMap.get( "id" ), dataMap.get( "fisrtName" ), dataMap.get( "lastName" ) );
            eleveRepository.add( eleve );
            assertTrue( eleveRepository.getAll().contains( eleve ) );
        } );
    }

}
