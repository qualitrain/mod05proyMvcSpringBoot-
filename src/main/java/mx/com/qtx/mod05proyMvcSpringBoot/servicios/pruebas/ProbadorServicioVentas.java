package mx.com.qtx.mod05proyMvcSpringBoot.servicios.pruebas;

import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.DetalleVenta;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Venta;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.GestorVentas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProbadorServicioVentas implements CommandLineRunner {
    final private Logger log = LoggerFactory.getLogger(ProbadorServicioVentas.class);

    final private GestorVentas servicioVtas;

    public ProbadorServicioVentas(GestorVentas servicioVtas) {
        this.servicioVtas = servicioVtas;
    }


    @Override
    public void run(String... args) throws Exception {
        try {
    //        probar_insercionVentaAgregada();
            probar_insercionVentaAgregada_conArtInexistente();
        }
        catch (Exception ex){
            log.error("{}:{}",ex.getClass().getName(), ex.getMessage());
        }
    }

    private void probar_insercionVentaAgregada() {
        Venta vta = new Venta();

        Persona cte = new Persona();
        cte.setIdPersona(3);
        // Irían mas datos, que en realidad no se usan en la prueba

        Persona vendedor = new Persona();
        vendedor.setIdPersona(4);
        // Irían mas datos, que en realidad no se usan en la prueba

        Articulo art1 = new Articulo();
        art1.setCveArticulo("A-24");

        Articulo art2 = new Articulo();
        art2.setCveArticulo("DR-56");

        Articulo art3 = new Articulo();
        art3.setCveArticulo("Y-22-78");

        List<DetalleVenta> dets = List.of(
                new DetalleVenta(1,art1,2, new BigDecimal("555.25")),
                new DetalleVenta(2,art2,1, new BigDecimal("600.50")),
                new DetalleVenta(3,art3,4, new BigDecimal("701.30"))
        );

        vta.setFecha(LocalDate.now());
        vta.setDetallesVta(dets);
        vta.setCliente(cte);
        vta.setVendedor(vendedor);

        int numVta = servicioVtas.registrarVenta(vta);
        log.info("Vta insertada: {}", numVta);
    }

    private void probar_insercionVentaAgregada_conArtInexistente() {
        Venta vta = new Venta();

        Persona cte = new Persona();
        cte.setIdPersona(3);
        // Irían mas datos, que en realidad no se usan en la prueba

        Persona vendedor = new Persona();
        vendedor.setIdPersona(4);
        // Irían mas datos, que en realidad no se usan en la prueba

        Articulo art1 = new Articulo();
        art1.setCveArticulo("A-24");

        Articulo art2 = new Articulo();
        art2.setCveArticulo("DR-56");

        Articulo art3 = new Articulo();
        art3.setCveArticulo("E-88000");

        List<DetalleVenta> dets = List.of(
                new DetalleVenta(1,art1,2, new BigDecimal("555.25")),
                new DetalleVenta(2,art2,1, new BigDecimal("600.50")),
                new DetalleVenta(3,art3,4, new BigDecimal("701.30"))
        );

        vta.setFecha(LocalDate.now());
        vta.setDetallesVta(dets);
        vta.setCliente(cte);
        vta.setVendedor(vendedor);

        int numVta = servicioVtas.registrarVenta(vta);
        log.info("Vta insertada: {}", numVta);
    }

}
