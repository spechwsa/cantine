package com.spechwsa.cantine.domain.repositories;

import java.util.Set;

import com.spechwsa.cantine.domain.Enseignant;

public interface EnseignantRepository {

    void add( Enseignant enseignant );

    Set<Enseignant> getAll();

}
