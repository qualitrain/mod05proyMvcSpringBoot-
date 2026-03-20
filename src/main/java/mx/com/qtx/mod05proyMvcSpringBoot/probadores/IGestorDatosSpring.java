package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;

public interface IGestorDatosSpring {
    Persona leerPersonaXID(int idPersona);
}
