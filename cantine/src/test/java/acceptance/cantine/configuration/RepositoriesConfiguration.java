package acceptance.cantine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.spechwsa.cantine.adapters.InMemoryCantineRepository;
import com.spechwsa.cantine.adapters.InMemoryEleveRepository;
import com.spechwsa.cantine.adapters.InMemoryEnseignantRepository;
import com.spechwsa.cantine.domain.repositories.CantineRepository;
import com.spechwsa.cantine.domain.repositories.EleveRepository;
import com.spechwsa.cantine.domain.repositories.EnseignantRepository;

@Configuration
public class RepositoriesConfiguration {

    @Bean
    @Scope( "cucumber-glue" ) // in order to have cucumber-spring remove it
                              // after each step
    public EnseignantRepository enseignantRepository() {
        return new InMemoryEnseignantRepository();
    }

    @Bean
    @Scope( "cucumber-glue" ) // in order to have cucumber-spring remove it
                              // after each step
    public EleveRepository eleveRepository() {
        return new InMemoryEleveRepository();
    }

    @Bean
    @Scope( "cucumber-glue" ) // in order to have cucumber-spring remove it
                              // after each step
    public CantineRepository cantineRepository() {
        return new InMemoryCantineRepository();
    }
}
