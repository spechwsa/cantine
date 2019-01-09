package com.spechwsa.cantine.adapters;

import java.util.LinkedHashSet;
import java.util.Set;

import com.spechwsa.cantine.domain.Eleve;
import com.spechwsa.cantine.domain.repositories.CantineRepository;

public class InMemoryCantineRepository implements CantineRepository {

    private final Set<Eleve> eleves = new LinkedHashSet<>();

    @Override
    public void add( Eleve eleve ) {
        eleves.add( eleve );
    }

    @Override
    public Set<Eleve> getAll() {

        return eleves;
    }

}
