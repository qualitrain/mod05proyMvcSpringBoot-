package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import jakarta.annotation.PostConstruct;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.persistencia.GestorBD_MySQL;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.IGestorBD;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.ILogPersona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProbadorIGestorBD implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(ProbadorIGestorBD.class);

    private final IGestorBD gestorBD;
    private final ILogPersona auditorPersona;

    public ProbadorIGestorBD(IGestorBD gestorBD, ILogPersona auditorPersona) {
        this.auditorPersona = auditorPersona;
        log.info("Se invocó al constructor de ProbadorIGestorBD");
        this.gestorBD = gestorBD;
    }

    @PostConstruct
    public void reportarFinConstruccionBean(){
        log.info("Spring ha terminado de construir este bean " + this.hashCode());
    }

    public void saludar(){
        log.info("Hola a todos. Ha quedado registrada esta entrada histórica");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Corriendo en el Hilo {}", Thread.currentThread().toString());
        this.saludar();
        for(int i = 1; i<15; i++) {
            Persona perI = this.gestorBD.leerPersonaXID(this.getIdAleatorio());
            if(perI != null)
                log.info("Se ha leído a la persona con id = 1 " + perI.toString());
            else{
                log.warn("La persona con id " + i + " no existe");
            }
        }
        List<Integer> folios = auditorPersona.getFolios();
        folios.forEach(folioI->this.auditorPersona.consultarOperacion(folioI));
    }

    public int getIdAleatorio(){
        return (((int)(Math.random() * 15000))%10 ) + 1;
    }
}
