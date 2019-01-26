package com.spechwsa.cantine.domain;

public class EnseignantNonAutentifieException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final Enseignant  enseignant;

    public EnseignantNonAutentifieException( Enseignant enseignant ) {
        this.enseignant = enseignant;
    }

    @Override
    public String getMessage() {
        // TODO Auto-generated method stub
        return "Authentification préalable obiligatoire: l'enseignant " + enseignant.getLastName()
                + " n'est pas authentifié";
    }

}
