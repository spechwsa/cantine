package com.spechwsa.cantine.domain;

public class Enseignant {
    private String id;
    private String firstName;
    private String lastName;

    public Enseignant( String id, String firstName, String lastName ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Enseignant [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";
    }

    public String getLastName() {
        // TODO Auto-generated method stub
        return lastName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( firstName == null ) ? 0 : firstName.hashCode() );
        result = prime * result + ( ( id == null ) ? 0 : id.hashCode() );
        result = prime * result + ( ( lastName == null ) ? 0 : lastName.hashCode() );
        return result;
    }

    @Override
    public boolean equals( Object obj ) {
        if ( this == obj )
            return true;
        if ( obj == null )
            return false;
        if ( getClass() != obj.getClass() )
            return false;
        Enseignant other = (Enseignant) obj;
        if ( firstName == null ) {
            if ( other.firstName != null )
                return false;
        } else if ( !firstName.equals( other.firstName ) )
            return false;
        if ( id == null ) {
            if ( other.id != null )
                return false;
        } else if ( !id.equals( other.id ) )
            return false;
        if ( lastName == null ) {
            if ( other.lastName != null )
                return false;
        } else if ( !lastName.equals( other.lastName ) )
            return false;
        return true;
    }

}
