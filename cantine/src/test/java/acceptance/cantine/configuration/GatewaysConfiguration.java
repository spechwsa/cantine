package acceptance.cantine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.spechwsa.cantine.adapters.InMemoryAuthentificationGateway;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;

@Configuration
public class GatewaysConfiguration {
    @Bean
    public AuthenticationGateway authenticationGateway() {
        return new InMemoryAuthentificationGateway();
    }

}
