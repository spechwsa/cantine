package com.spechwsa.cantine.domain;

public class Eleve {
    private String id;
    private String firstName;
    private String lastName;

    public Eleve( String id, String firstName, String lastName ) {
        super();
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Eleve [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }

    public String getLastName() {
        // TODO Auto-generated method stub
        return lastName;
    }
}
