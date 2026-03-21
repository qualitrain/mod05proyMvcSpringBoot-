package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.DetalleVenta;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Venta;

import java.util.List;

public interface IGestorDatosSpring {
    Persona leerPersonaXID(int idPersona);
    Venta leerVentaXID(int numVta);
    List<DetalleVenta> leerDetallesVenta(int numVta);
    Articulo leerArticuloXID(String cveArt);
}
