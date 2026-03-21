package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.PersonaDTO;

import java.util.List;

public interface ILogPersona {
    int guardarOperacion(String tipoOperacion, PersonaDTO persona);
    void consultarOperacion(int folio);
    List<Integer> getFolios();
}
