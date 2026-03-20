package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.persistencia.jdbctemplate.GestorDatosJdbcTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProbadorGestorBdSpring implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(ProbadorGestorBdSpring.class);

    @Autowired
    IGestorDatosSpring gestorBD;

    @Override
    public void run(String... args) throws Exception {
        probar_leerPersonaXID(5);
    }

    private void probar_leerPersonaXID(int idPersona) {
        Persona persona = gestorBD.leerPersonaXID(idPersona);
        if(persona == null){
            log.warn("Persona con id {} NO EXISTE", idPersona);
        }
        else {
            log.info("Persona leida: {}", persona.toString());
        }
    }
}
