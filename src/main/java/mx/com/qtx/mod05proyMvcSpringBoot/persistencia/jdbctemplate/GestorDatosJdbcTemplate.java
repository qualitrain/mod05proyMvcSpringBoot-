package mx.com.qtx.mod05proyMvcSpringBoot.persistencia.jdbctemplate;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.ArticuloDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.DetalleVentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.PersonaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.VentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.IGestorDatosSpring;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
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

    @Override
    public VentaDTO insertarVenta(VentaDTO vta){
        String sql = "INSERT into venta (fecha_venta, id_persona_cte, id_persona_vendedor) values (?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int nRows = this.jdbcTemplate.update(
                con->{
                    PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                    ps.setObject(1,vta.getFechaVenta());
                    ps.setInt(2,vta.getIdPersonaCte());
                    ps.setInt(3,vta.getIdPersonaVendedor());
                    return ps;
                }, keyHolder
        );
        if(nRows == 0)
            return null;

        Number key = keyHolder.getKey();
        if(key == null)
            return null;

        vta.setNumVenta(keyHolder.getKey().intValue());
        return vta;

    }

    @Override
    public int insertarDetalleVenta(DetalleVentaDTO detVta){
        final String sql = "INSERT INTO detalle_venta (num_venta, num_detalle, cantidad, cve_articulo, precio_unitario) "
                +          "VALUES (?, ?, ?, ?, ?);";
        return this.jdbcTemplate.update(sql, detVta.getNumVenta(), detVta.getNumDetalle(), detVta.getCantidad(),
                                             detVta.getCveArticulo(), detVta.getPrecioUnitario());
    }

    @Override
    public int insertarPersona(PersonaDTO persona) {
        if(personaInvalida(persona)){
            throw new IllegalArgumentException("registro de persona inválido " +  persona);
        }
        final String cadSQL ="INSERT INTO persona (id_persona, nombre, direccion, fecha_nacimiento) " +
                "VALUES (?, ?, ?, ?);";

        return  this.jdbcTemplate.update(cadSQL, persona.getIdPersona(), persona.getNombre(), persona.getDireccion(),
                persona.getFechaNacimiento());
    }

    private boolean personaInvalida(PersonaDTO persona) {
        if(persona.getIdPersona() < 0)
            return true;
        if(persona.getNombre() == null)
            return true;
        if(persona.getDireccion() == null)
            return true;
        if(persona.getFechaNacimiento() == null)
            return true;
        return false;
    }

}
