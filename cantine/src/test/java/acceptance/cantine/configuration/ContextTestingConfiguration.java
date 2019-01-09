package acceptance.cantine.configuration;

import cucumber.api.java.Before;
import org.springframework.test.context.ContextConfiguration;

/*
 * Voir https://www.baeldung.com/cucumber-spring-integration ne marche plus avec 2 class de steps car
 * essai de creer deux fois le context.
 * voir https://github.com/cucumber/cucumber-jvm/issues/1420
 * utiliser la solution du before mais il faut mettre tous dans le même package.
*/
@ContextConfiguration( classes = {
        RepositoriesConfiguration.class,
        GatewaysConfiguration.class
} )
public class ContextTestingConfiguration {
    @Before
    public void setup_cucumber_spring_context() {
        // Dummy method so cucumber will recognize this class as glue
        // and use its context configuration.
    }
}
