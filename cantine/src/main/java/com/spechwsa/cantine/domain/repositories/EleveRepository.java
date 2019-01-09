package com.spechwsa.cantine.domain.repositories;

import java.util.Set;

import com.spechwsa.cantine.domain.Eleve;

public interface EleveRepository {
    void add( Eleve eleve );

    Set<Eleve> getAll();
}
