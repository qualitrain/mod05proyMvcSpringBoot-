package mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio;

import java.time.LocalDate;
import java.util.List;

public class Venta {
    private int numVta;
    private LocalDate fecha;
    private Persona cliente;
    private Persona vendedor;
    private List<DetalleVenta> detallesVta;
}
