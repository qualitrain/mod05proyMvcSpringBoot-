package mx.com.qtx.mod05proyMvcSpringBoot.persistencia.jdbctemplate;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Venta;
import mx.com.qtx.mod05proyMvcSpringBoot.probadores.IGestorDatosSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

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

    public Venta leerVentaXID(int numVta){
        final String sql = "SELECT num_venta, fecha_venta, id_persona_cte, id_persona_vendedor FROM venta " +
                "WHERE num_venta = ?";
        try{
            return jdbcTemplate.queryForObject(sql,
                    (rs,nRow)->{
                        Venta vta = new Venta();
                        vta.setNumVenta( rs.getInt("num_venta"));
                        vta.setFechaVenta( rs.getObject("fecha_venta", LocalDate.class));
                        vta.setIdPersonaCte( rs.getInt("id_persona_cte"));
                        vta.setIdPersonaCte( rs.getInt("id_persona_vendedor"));
                        return vta;
                    },
                    numVta);
        }
        catch(EmptyResultDataAccessException erdaex){
            return null;
        }
    }
}
