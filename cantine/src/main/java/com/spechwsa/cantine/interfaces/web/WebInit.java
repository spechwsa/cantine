package com.spechwsa.cantine.interfaces.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import com.spechwsa.cantine.Application;

// pour la creation de la premiere servlet en cas de génération de war
public class WebInit extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure( SpringApplicationBuilder builder ) {
        
        return builder.sources( Application.class );
    }

}
