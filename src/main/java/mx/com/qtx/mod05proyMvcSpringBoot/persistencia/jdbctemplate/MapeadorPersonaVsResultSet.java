package mx.com.qtx.mod05proyMvcSpringBoot.persistencia.jdbctemplate;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MapeadorPersonaVsResultSet implements RowMapper<Persona> {
    @Override
    public Persona mapRow(ResultSet rs, int rowNum) throws SQLException {

        Persona persona = new Persona();
        persona.setIdPersona(rs.getInt("id_persona"));
        persona.setDireccion(rs.getString("direccion"));
        persona.setNombre(rs.getString("nombre"));
        persona.setFechaNacimiento(rs.getObject("fecha_nacimiento",LocalDate.class));
        return persona;
    }
}
