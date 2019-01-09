package com.spechwsa.cantine.domain;

import java.util.Set;

import com.spechwsa.cantine.domain.repositories.CantineRepository;

public class Cantine {
    private CantineRepository cantineRepository;

    public Cantine( CantineRepository cantineRepository ) {
        this.cantineRepository = cantineRepository;
    }

    public void enregisterPresence( Eleve eleve ) {
        cantineRepository.add( eleve );
    }

    public Set<Eleve> getCantineDuJour() {
        return cantineRepository.getAll();
    }

}
