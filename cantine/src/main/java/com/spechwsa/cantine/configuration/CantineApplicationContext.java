package com.spechwsa.cantine.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.spechwsa.cantine.adapters.InMemoryAuthentificationGateway;
import com.spechwsa.cantine.adapters.InMemoryCantineRepository;
import com.spechwsa.cantine.adapters.InMemoryEleveRepository;
import com.spechwsa.cantine.adapters.InMemoryEnseignantRepository;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;
import com.spechwsa.cantine.domain.repositories.CantineRepository;
import com.spechwsa.cantine.domain.repositories.EleveRepository;
import com.spechwsa.cantine.domain.repositories.EnseignantRepository;
import com.spechwsa.cantine.domain.services.ServiceCantine;

/**
 * Application cantine. Utilise le framework SPRING BOOT Creation des Bean
 * Initiaux voir
 * https://dzone.com/articles/playing-sround-with-spring-bean-configuration
 * http://www.springboottutorial.com/spring-boot-java-xml-context-configuration
 */

@Configuration
public class CantineApplicationContext {

    AuthenticationGateway autentificationGateway;

    @Bean("authenticationGateway")
    public AuthenticationGateway AuthenticationGateway() {
        autentificationGateway = new InMemoryAuthentificationGateway();
        return autentificationGateway;
    }

    CantineRepository cantineRepository;

    @Bean("cantineRepository")
    public CantineRepository CantineRepository() {
        cantineRepository = new InMemoryCantineRepository();
        return cantineRepository;
    }

    @Bean
    @DependsOn({"authenticationGateway","cantineRepository"})
    public ServiceCantine ServiceCantine() {
        return new ServiceCantine( autentificationGateway, cantineRepository );
    }

    @Bean
    public EnseignantRepository enseignantRepository() {
        return new InMemoryEnseignantRepository();
    }

    @Bean
    public EleveRepository eleveRepository() {
        return new InMemoryEleveRepository();
    }
}
