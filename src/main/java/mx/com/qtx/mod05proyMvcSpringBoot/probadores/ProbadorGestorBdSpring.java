package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.ArticuloDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.DetalleVentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.PersonaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.VentaDTO;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.IGestorDatosSpring;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ProbadorGestorBdSpring implements CommandLineRunner {
    private static Logger log = LoggerFactory.getLogger(ProbadorGestorBdSpring.class);

    @Autowired
    IGestorDatosSpring gestorBD;

    @Override
    public void run(String... args) throws Exception {
        try {
            /*
            probar_leerPersonaXID(5);
            probar_leerVentaXID(40);
            probar_leerDetallesVenta(40);
            probar_leerArticuloXID("A-23");
            String consultaSql = """
                    select * from venta vta
                    join detalle_venta det on vta.num_venta = det.num_venta
                    where vta.num_venta < 15;
                    """;
            probar_exportarDatos(consultaSql);
            probar_insertarDetalleVenta();
             */
            probar_insertarDetalleVenta_artNoExiste();

        }
        catch (Exception ex){
            log.error("{}:{}",ex.getClass().getName(),ex.getMessage());
        }

    }

    private void probar_exportarDatos(String sql) {
        Integer nRegistros = gestorBD.exportarDatos(sql);
        log.info("ha procesado {] registros", nRegistros);

    }

    private void probar_leerArticuloXID(String cveArt) {
        ArticuloDTO art = gestorBD.leerArticuloXID(cveArt);
        if(art == null){
            log.warn("Articulo con cve {} NO EXISTE", cveArt);
        }
        else {
            log.info("Articulo leido: {}", art.toString());
        }
    }

    private void probar_leerPersonaXID(int idPersona) {
        PersonaDTO persona = gestorBD.leerPersonaXID(idPersona);
        if(persona == null){
            log.warn("Persona con id {} NO EXISTE", idPersona);
        }
        else {
            log.info("Persona leida: {}", persona.toString());
        }
    }

    private void probar_leerVentaXID(int numVta){
        VentaDTO vta = gestorBD.leerVentaXID(numVta);
        if(vta == null){
            log.warn("Venta con id {} NO EXISTE", numVta);
        }
        else {
            log.info("Venta leida: {}", vta.toString());
        }
    }

    private void probar_leerDetallesVenta(int numVta){
        List<DetalleVentaDTO> dets = gestorBD.leerDetallesVenta(numVta);
        if(dets.size() == 0){
            log.warn("Venta {} no tiene detalles ", numVta);
        }
        else{
            for (DetalleVentaDTO detI : dets) {
                log.info("   {}",detI.toString());
            }
        }
    }

    private void probar_insertarVenta(){
        VentaDTO vta = new VentaDTO();
        vta.setFechaVenta(LocalDate.now());
        vta.setIdPersonaCte(5);
        vta.setIdPersonaVendedor(1);

        VentaDTO vtaInsertada = gestorBD.insertarVenta(vta);
        log.info("Vta insertada: {}", vtaInsertada.toString());
    }

    private void probar_insertarDetalleVenta(){
        DetalleVentaDTO detVta = new DetalleVentaDTO();
        detVta.setNumVenta(54);
        detVta.setNumDetalle(2);
        detVta.setCveArticulo("A-23");
        detVta.setCantidad(5);
        detVta.setPrecioUnitario(530.00f);

        int nRows = this.gestorBD.insertarDetalleVenta(detVta);
        if(nRows > 0){
            log.info("Detalle insertado {}",detVta.toString());
        }
    }

    private void probar_insertarDetalleVenta_artNoExiste(){
        DetalleVentaDTO detVta = new DetalleVentaDTO();
        detVta.setNumVenta(54);
        detVta.setNumDetalle(3);
        detVta.setCveArticulo("1-1000");
        detVta.setCantidad(1);
        detVta.setPrecioUnitario(670.00f);

        int nRows = this.gestorBD.insertarDetalleVenta(detVta);
        if(nRows > 0){
            log.info("Detalle insertado {}",detVta.toString());
        }
    }

    private void probar_insertarPersona() {
        PersonaDTO p = new PersonaDTO();
        p.setIdPersona(102);
        p.setFechaNacimiento(LocalDate.of(2003,2,14));
        p.setDireccion("Av. Emiliano Zapata 201, col. Narvarte");
        p.setNombre("Mariana Lora Morral");
        if(this.gestorBD.insertarPersona(p) > 0)
            log.info("persona insertada: {}",p.toString());
        else
            log.info("insercion rechazada: {}", p.toString());
    }
}
