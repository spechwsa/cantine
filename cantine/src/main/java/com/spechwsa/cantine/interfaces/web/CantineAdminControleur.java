package com.spechwsa.cantine.interfaces.web;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import com.spechwsa.cantine.domain.Eleve;
import com.spechwsa.cantine.domain.Enseignant;
import com.spechwsa.cantine.domain.EnseignantNonAutentifieException;
import com.spechwsa.cantine.domain.gateways.AuthenticationGateway;
import com.spechwsa.cantine.domain.repositories.EleveRepository;
import com.spechwsa.cantine.domain.services.ServiceCantine;

@Controller
@RequestMapping("/Cantine")
public class CantineAdminControleur {
    
    private static final String VUE_ADMIN_INSCRIPTION = "admin/AdminInscription";

    @Autowired
    private ServiceCantine serviceCantine;
    
    @Autowired
    private EleveRepository eleveRepository; 
    
    @Autowired
    private AuthenticationGateway authentificationGateway;

    @RequestMapping("/InscrireEleves")
    public String index(Model model) {
        
        authentificationGateway.authenticate(  new Enseignant( "a", "Françoise", "MULLER" ) );
        try {
            serviceCantine.enregisterPresence( new Enseignant( "a", "Françoise", "MULLER" ), new Eleve( "a", "François" , "THOMAS" ) );
        } catch ( EnseignantNonAutentifieException e ) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        Map<Eleve,Boolean> eleveCantineMap = eleveRepository.getAll().
                                            stream().collect( 
                                            Collectors.toMap( 
                                                    eleve -> {return eleve;} ,
                                                    eleve -> {return serviceCantine.estEnregistreAujourdhui(eleve);} 
                                                    ) 
                                            );    

                
      //On stocke les élèvesCantine dans le Model sous l'attribut "EleveCantineMap"
        model.addAttribute("EleveCantineMap",eleveCantineMap);
       
        return VUE_ADMIN_INSCRIPTION;
    }

}
