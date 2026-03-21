package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.ArticuloDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.DetalleVentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.PersonaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.VentaDTO;

import java.util.List;

public interface IGestorDatosSpring {
    PersonaDTO leerPersonaXID(int idPersona);
    VentaDTO leerVentaXID(int numVta);
    List<DetalleVentaDTO> leerDetallesVenta(int numVta);
    ArticuloDTO leerArticuloXID(String cveArt);
    Integer exportarDatos(String sql);
}
