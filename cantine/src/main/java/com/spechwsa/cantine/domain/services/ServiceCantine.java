package com.spechwsa.cantine.domain.services;

import java.util.Set;

import com.spechwsa.cantine.domain.Cantine;
import com.spechwsa.cantine.domain.Eleve;
import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.EnseignantNonAutentifieException;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;
import com.spechwsa.cantine.domain.repositories.CantineRepository;

public class ServiceCantine {

    private final AuthenticationGateway autentificationGateway;
    private final Cantine               cantine;

    public ServiceCantine( AuthenticationGateway autentificationGateway, CantineRepository cantineRepository ) {
        this.autentificationGateway = autentificationGateway;
        cantine = new Cantine( cantineRepository );
        System.out.println( "Service Cantine Created" );
    }

    public void enregisterPresence( Enseignant enseignant, Eleve eleve ) throws EnseignantNonAutentifieException {
        if ( autentificationGateway.currentEnseignant().isPresent()
                && autentificationGateway.currentEnseignant().get().equals( enseignant ) )
            cantine.enregisterPresence( eleve );
        else {
            throw ( new EnseignantNonAutentifieException( enseignant ) );
        }

    }

    public Set<Eleve> getCantineDuJour() {
        return cantine.getCantineDuJour();
    }

    public boolean estEnregistreAujourdhui( Eleve eleve ) {
        // TODO Auto-generated method stub
        return cantine.estEnregistreAujourdhui( eleve );
    }

}
