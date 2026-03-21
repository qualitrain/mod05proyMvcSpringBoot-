package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.DetalleVenta;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Venta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProbadorGestorBdSpring implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(ProbadorGestorBdSpring.class);

    @Autowired
    IGestorDatosSpring gestorBD;

    @Override
    public void run(String... args) throws Exception {
        probar_leerPersonaXID(5);
        probar_leerVentaXID(40);
        probar_leerDetallesVenta(40);
        probar_leerArticuloXID("A-23");
    }

    private void probar_leerArticuloXID(String cveArt) {
        Articulo art = gestorBD.leerArticuloXID(cveArt);
        if(art == null){
            log.warn("Articulo con cve {} NO EXISTE", cveArt);
        }
        else {
            log.info("Articulo leido: {}", art.toString());
        }
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

    private void probar_leerVentaXID(int numVta){
        Venta vta = gestorBD.leerVentaXID(numVta);
        if(vta == null){
            log.warn("Venta con id {} NO EXISTE", numVta);
        }
        else {
            log.info("Venta leida: {}", vta.toString());
        }
    }

    private void probar_leerDetallesVenta(int numVta){
        List<DetalleVenta> dets = gestorBD.leerDetallesVenta(numVta);
        if(dets.size() == 0){
            log.warn("Venta {} no tiene detalles ", numVta);
        }
        else{
            for (DetalleVenta detI : dets) {
                log.info("   {}",detI.toString());
            }
        }
    }

}
