package com.spechwsa.cantine.interfaces.web;

import com.spechwsa.cantine.domain.Eleve;

public class EleveCantineDTO {
    private String id;
    public String getId() {
        return id;
    }
    
    private String firstName;
    public String getFirstName() {
        return firstName;
    }

    private String lastName;
    public String getLastName() {
        // TODO Auto-generated method stub
        return lastName;
    }

    private Boolean estPresent;
    public Boolean getEstPresent() {
        return estPresent;
    }
    
    public EleveCantineDTO( Eleve eleve, Boolean estPresent ) {
        this.id = eleve.getId();
        this.firstName = eleve.getFirstName();
        this.lastName = eleve.getLastName();
        this.estPresent = estPresent;
    }
    
    
    

}
