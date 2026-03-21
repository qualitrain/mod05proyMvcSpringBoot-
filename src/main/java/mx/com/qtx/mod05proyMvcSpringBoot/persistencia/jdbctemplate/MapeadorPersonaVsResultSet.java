package mx.com.qtx.mod05proyMvcSpringBoot.persistencia.jdbctemplate;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.PersonaDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class MapeadorPersonaVsResultSet implements RowMapper<PersonaDTO> {
    @Override
    public PersonaDTO mapRow(ResultSet rs, int rowNum) throws SQLException {

        PersonaDTO persona = new PersonaDTO();
        persona.setIdPersona(rs.getInt("id_persona"));
        persona.setDireccion(rs.getString("direccion"));
        persona.setNombre(rs.getString("nombre"));
        persona.setFechaNacimiento(rs.getObject("fecha_nacimiento",LocalDate.class));
        return persona;
    }
}
