package mx.com.qtx.mod05proyMvcSpringBoot.core;


import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.PersonaDTO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IGestorBD {
    Connection getConexionBD() throws SQLException;
    List<PersonaDTO> getPersonasTodas() throws SQLException;
    int insertarPersona(PersonaDTO persona) throws SQLException;
    PersonaDTO leerPersonaXID(int id) throws SQLException;
}
