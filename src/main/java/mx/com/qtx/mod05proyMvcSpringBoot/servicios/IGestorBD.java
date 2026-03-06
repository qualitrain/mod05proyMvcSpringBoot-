package mx.com.qtx.mod05proyMvcSpringBoot.servicios;


import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface IGestorBD {
    Connection getConexionBD() throws SQLException;
    List<Persona> getPersonasTodas() throws SQLException;
    int insertarPersona(Persona persona) throws SQLException;
    Persona leerPersonaXID(int id) throws SQLException;
}
