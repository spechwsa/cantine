package com.spechwsa.cantine.adapters;

import java.util.Optional;

import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;

public class InMemoryAuthentificationGateway implements AuthenticationGateway {
    private Enseignant enseignant;

    @Override
    public void authenticate( Enseignant enseignant ) {
        this.enseignant = enseignant;

    }

    @Override
    public Optional<Enseignant> currentEnseignant() {
        // TODO Auto-generated method stub
        return Optional.ofNullable( enseignant );
    }

    @Override
    public void UnAuthenticate( Enseignant enseignant ) {
        if ( enseignant.equals( this.enseignant ) ) {
            this.enseignant = null;
        }

    }

}
