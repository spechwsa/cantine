package com.spechwsa.cantine.interfaces.web;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.spechwsa.cantine.domain.Eleve;
import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.EnseignantNonAutentifieException;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;
import com.spechwsa.cantine.domain.repositories.EleveRepository;
import com.spechwsa.cantine.domain.services.ServiceCantine;

@Controller
@RequestMapping( CantineAdminControleur.URL_DE_BASE )
public class CantineAdminControleur {

    static final String           URL_DE_BASE                              = "/Cantine";

    private static final String   MODEL_ATTIBUTE_ELEVE_CANTINE_MAP         = "EleveCantineMap";
    private static final String   MODEL_ATTIBUTE_NB_ELEMENT_PAR_PAGE       = "NbElementParPage";
    private static final String   MODEL_ATTIBUTE_PAGE_COURANTE             = "Page";
    private static final String   MODEL_ATTRIBUT_NB_TOTAL_PAGES            = "NbTotalPages";

    private static final String   URL_PARAMETRE_NB_ELEMENT_PAR_PAGE        = "size";
    private static final String   URL_PARAMETRE_NB_ELEMENT_PAR_PAGE_DEFAUT = "5";
    private static final String   URL_PARAMETRE_NUMERO_PAGE                = "page";
    private static final String   URL_PARAMETRE_NUMERO_PAGE_DEFAUT         = "0";
    private static final String   URL_INSCRIRE_ELEVES                      = "/InscrireEleves";
    
    private static final String   VUE_ADMIN_INSCRIPTION                    = "admin/AdminInscription";
    private static final String   VUE_FRAGMENT_LIST_ELEVES                 = "admin/listEleves";

    @Autowired
    private ServiceCantine        serviceCantine;

    @Autowired
    private EleveRepository       eleveRepository;

    @Autowired
    private AuthenticationGateway authentificationGateway;

    @RequestMapping( URL_INSCRIRE_ELEVES )
    public String index( Model model ) {

        initialisationPourTestEtASuprimer();
        int nbElementParPage = 8;
        int nbTotalPages = 1;
        if ( eleveRepository.getAll().size() != 0 )
            nbTotalPages = 1 + ( eleveRepository.getAll().size() - 1 ) / nbElementParPage;
        model.addAttribute( MODEL_ATTIBUTE_NB_ELEMENT_PAR_PAGE, nbElementParPage );
        model.addAttribute( MODEL_ATTRIBUT_NB_TOTAL_PAGES, nbTotalPages );
        return VUE_ADMIN_INSCRIPTION;
    }

    @RequestMapping( "/listEleves" )
    public String getlistEleves( Model model,
            @RequestParam( name = URL_PARAMETRE_NUMERO_PAGE, defaultValue = URL_PARAMETRE_NUMERO_PAGE_DEFAUT ) int page,
            @RequestParam( name = URL_PARAMETRE_NB_ELEMENT_PAR_PAGE, defaultValue = URL_PARAMETRE_NB_ELEMENT_PAR_PAGE_DEFAUT ) int nbElementParPage ) {

        return miseAJourVueFragmentListEleve( model, page, nbElementParPage );
    }

    @RequestMapping( "/changeInscription" )
    public String changeInscription( Model model,
            @RequestParam( name = URL_PARAMETRE_NUMERO_PAGE, defaultValue = URL_PARAMETRE_NUMERO_PAGE_DEFAUT ) int page,
            @RequestParam( name = URL_PARAMETRE_NB_ELEMENT_PAR_PAGE, defaultValue = URL_PARAMETRE_NB_ELEMENT_PAR_PAGE_DEFAUT ) int nbElementParPage,
            @RequestParam( name = "id" ) String id ) {
        miseAJourInscription( id );
        return miseAJourVueFragmentListEleve( model, page, nbElementParPage );
    }

    private String miseAJourVueFragmentListEleve( Model model, int page, int nbElementParPage ) {
        Map<Eleve, Boolean> eleveCantineMap = creeMapEleveCantine( page - 1, nbElementParPage );

        // On stocke élèvesCantine dans le Model sous l'attribut
        // "EleveCantineMap"
        model.addAttribute( MODEL_ATTIBUTE_ELEVE_CANTINE_MAP, eleveCantineMap );
        model.addAttribute( MODEL_ATTIBUTE_PAGE_COURANTE, page );
        model.addAttribute( MODEL_ATTIBUTE_NB_ELEMENT_PAR_PAGE, nbElementParPage );

        return VUE_FRAGMENT_LIST_ELEVES;
    }

    private void miseAJourInscription( String id ) {
        // récupération de l'éléve
        Optional<Eleve> optionalEleve = eleveRepository.getAll().stream()
                .filter( c -> c.getId().equals( id ) ).findFirst();

        if ( optionalEleve.isPresent() ) {
            Eleve eleve = optionalEleve.get();
            if ( serviceCantine.estEnregistreAujourdhui( eleve ) ) {
                // on le désincrit
                try {
                    // ToDo remplacer l'enseignat en dur
                    serviceCantine.deEnregisterPresence( new Enseignant( "a", "Françoise", "MULLER" ), eleve );
                } catch ( EnseignantNonAutentifieException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }           } else {
                // on l'inscrit
                try {
                    // ToDo remplacer l'enseignat en dur
                    serviceCantine.enregisterPresence( new Enseignant( "a", "Françoise", "MULLER" ), eleve );
                } catch ( EnseignantNonAutentifieException e ) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    private Map<Eleve, Boolean> creeMapEleveCantine( int p, int s ) {
        // Création de L'objet Map<Eleve, Boolean> pour affichage dans la vue
        // ordre alphabétique.
        // On crée une LinkedHashMap au lieux d'une HashMap pour préserver
        // l'ordre
        // en cas de doublon inséré on prend le premier
        Map<Eleve, Boolean> eleveCantineMap = eleveRepository.getAll().stream()
                .sorted( Comparator.comparing( Eleve::getLastName ) )
                .skip( p * s ).limit( s )
                .collect(
                        Collectors.toMap(
                                eleve -> { // construction clée
                                    return eleve;
                                },
                                eleve -> { // construction valeur
                                    return serviceCantine.estEnregistreAujourdhui( eleve );
                                },
                                ( e1, e2 ) -> e1, // gestion doublon
                                LinkedHashMap::new // type de Map générée
                        ) );
        return eleveCantineMap;
    }

    private void initialisationPourTestEtASuprimer() {
        authentificationGateway.authenticate( new Enseignant( "a", "Françoise", "MULLER" ) );
        try {
            serviceCantine.enregisterPresence( new Enseignant( "a", "Françoise", "MULLER" ),
                    new Eleve( "YOW72VJV9HQ", "Zia", "Ashley" ) );
        } catch ( EnseignantNonAutentifieException e ) {
            e.printStackTrace();
        }
    }

}
