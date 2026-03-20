package mx.com.qtx.mod05proyMvcSpringBoot.persistencia.jdbctemplate;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.probadores.IGestorDatosSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GestorDatosJdbcTemplate implements IGestorDatosSpring {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Persona leerPersonaXID(int idPersona){
        final String sql = "SELECT id_persona, nombre, direccion, fecha_nacimiento FROM persona " +
                "WHERE id_persona = ?";
        try {
           return jdbcTemplate.queryForObject(sql, new MapeadorPersonaVsResultSet(), idPersona);
        }
        catch(EmptyResultDataAccessException erdaex){
            return null;
        }
    }
}
