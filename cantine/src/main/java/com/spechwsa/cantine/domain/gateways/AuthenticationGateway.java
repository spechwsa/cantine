package com.spechwsa.cantine.domain.gateways;

import java.util.Optional;

import com.spechwsa.cantine.domain.Enseignant;

public interface AuthenticationGateway {

    void authenticate( Enseignant enseignant );

    void UnAuthenticate( Enseignant enseignant );

    Optional<Enseignant> currentEnseignant();

}
