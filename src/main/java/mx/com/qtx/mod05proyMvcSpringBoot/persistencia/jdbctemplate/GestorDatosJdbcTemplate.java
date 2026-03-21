package mx.com.qtx.mod05proyMvcSpringBoot.persistencia.jdbctemplate;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.ArticuloDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.DetalleVentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.PersonaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.VentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.IGestorDatosSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class GestorDatosJdbcTemplate implements IGestorDatosSpring {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public PersonaDTO leerPersonaXID(int idPersona){
        final String sql = "SELECT id_persona, nombre, direccion, fecha_nacimiento FROM persona " +
                "WHERE id_persona = ?";
        try {
           return jdbcTemplate.queryForObject(sql, new MapeadorPersonaVsResultSet(), idPersona);
        }
        catch(EmptyResultDataAccessException erdaex){
            return null;
        }
    }

    public VentaDTO leerVentaXID(int numVta){
        final String sql = "SELECT num_venta, fecha_venta, id_persona_cte, id_persona_vendedor FROM venta " +
                "WHERE num_venta = ?";
        try{
            return jdbcTemplate.queryForObject(sql,
                    (rs,nRow)->{
                        VentaDTO vta = new VentaDTO();
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

    public List<DetalleVentaDTO> leerDetallesVenta(int numVta){
        final String sql = """
                SELECT num_venta, num_detalle, cantidad, cve_articulo, precio_unitario 
                FROM detalle_venta 
                WHERE num_venta = ?
                """;
        return jdbcTemplate.query(sql,
                (rs,nRow)->{
                    DetalleVentaDTO detVtaI = new DetalleVentaDTO();
                    detVtaI.setNumVenta( rs.getInt("num_venta") );
                    detVtaI.setNumDetalle( rs.getInt("num_detalle") );
                    detVtaI.setCantidad( rs.getInt("cantidad") );
                    detVtaI.setCveArticulo( rs.getString("cve_articulo") );
                    detVtaI.setPrecioUnitario( rs.getFloat("precio_unitario"));
                    return detVtaI;
                },
                numVta
                );
       }

    public ArticuloDTO leerArticuloXID(String cveArt){
        final String sql = "SELECT cve_articulo, descripcion, costo_prov_1, precio_lista FROM articulo " +
                "WHERE cve_articulo = ?";
        try{
            return jdbcTemplate.queryForObject(sql,
                    (rs,nRow)->{
                        ArticuloDTO art = new ArticuloDTO();
                        art.setCveArticulo( rs.getString( "cve_articulo") );
                        art.setDescripcion( rs.getString("descripcion"));
                        art.setCostoProv1( rs.getFloat("costo_prov_1"));
                        art.setPrecioLista( rs.getFloat("precio_lista"));
                        return art;
                    },
                    cveArt);
        }
        catch(EmptyResultDataAccessException erdaex){
            return null;
        }
    }

    public Integer exportarDatos(String sql){
        return jdbcTemplate.query(sql, new ConsultadorDatosGenerico() );

    }
}
