package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import mx.com.qtx.mod05proyMvcSpringBoot.persistencia.GestorBD_MySQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ProbadorIGestorBD implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(ProbadorIGestorBD.class);

    public ProbadorIGestorBD() {
        log.info("Se invocó al constructor de ProbadorIGestorBD");
    }

    public void saludar(){
        log.info("Hola a todos. Ha quedado registrada esta entrada histórica");
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Corriendo en el Hilo {}", Thread.currentThread().toString());
        this.saludar();
    }
}
