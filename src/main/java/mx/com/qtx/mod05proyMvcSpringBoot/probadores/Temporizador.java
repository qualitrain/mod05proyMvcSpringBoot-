package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
public class Temporizador implements CommandLineRunner {
    private final int PAUSA_SEGUNDOS = 30;
    private long contador = 0;
    private static Logger log = LoggerFactory.getLogger(Temporizador.class);

    public void contar(){
        log.info("{}",contador);
        log.info("Corriendo en el Hilo {}", Thread.currentThread().toString());
        contador += 30;
        hacerPausa(PAUSA_SEGUNDOS);
    }

    private void hacerPausa(int pausaSegundos) {
        try {
            Thread.sleep(pausaSegundos * 1000);
        }
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Corriendo en el Hilo {}", Thread.currentThread().toString());
        this.contar();
    }
}

