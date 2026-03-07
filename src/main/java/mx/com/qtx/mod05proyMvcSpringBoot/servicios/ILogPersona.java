package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;

import java.util.List;

public interface ILogPersona {
    int guardarOperacion(String tipoOperacion, Persona persona);
    void consultarOperacion(int folio);
    List<Integer> getFolios();
}
