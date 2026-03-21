package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Venta;

public interface IGestorDatosSpring {
    Persona leerPersonaXID(int idPersona);
    Venta leerVentaXID(int numVta);
}
