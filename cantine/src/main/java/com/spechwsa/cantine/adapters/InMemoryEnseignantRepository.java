package com.spechwsa.cantine.adapters;

import java.util.LinkedHashSet;
import java.util.Set;

import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.repositories.EnseignantRepository;

public class InMemoryEnseignantRepository implements EnseignantRepository {
    private final Set<Enseignant> enseignants = new LinkedHashSet<>();

    @Override
    public void add( Enseignant enseignant ) {
        enseignants.add( enseignant );
    }

    @Override
    public Set<Enseignant> getAll() {
        // TODO Auto-generated method stub
        return enseignants;
    }

}
