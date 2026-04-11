package mx.com.qtx.mod05proyMvcSpringBoot.persistencia;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.PersonaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.probadores.IGestorBD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class GestorBD_MySQL implements IGestorBD {
    private static Logger log = LoggerFactory.getLogger(GestorBD_MySQL.class); ;
    private final DataSource ds;
//    private final ILogPersona logP;

    public GestorBD_MySQL(DataSource ds) {
        log.debug("GestorBD_MySQL.GestorBD_MySQL");
 //       this.logP = logP;
        this.ds = ds;
    }

    @Override
    public Connection getConexionBD() throws SQLException {
        try {
           return this.ds.getConnection();
        }
        catch (SQLException e) {
            throw e;
        }
    }

    public void desplegarPersonasConNombreyFecNac() throws SQLException {
        final String cadSQL = "SELECT id_persona, nombre, fecha_nacimiento FROM persona;";
        try(Connection con = this.getConexionBD()){
            try(Statement st = con.createStatement()){
                st.execute(cadSQL);
                try(ResultSet rs = st.getResultSet()){
                    while(rs.next()){
                       int id = rs.getInt("id_persona");
                       String nombre = rs.getString("nombre");
                       Date fechaNac = rs.getDate("fecha_nacimiento");

                       System.out.printf("%4d %-40s %s\n", id, nombre, fechaNac.toLocalDate());
                    }
                }
            }
        }
    }

    @Override
    public List<PersonaDTO> getPersonasTodas() throws SQLException {
        final String cadSQL = "SELECT id_persona, nombre, direccion, fecha_nacimiento FROM persona;";
        List<PersonaDTO> lstPersonas = new ArrayList<>();

        try(Connection con = this.getConexionBD()){
            try(Statement st = con.createStatement()){
                st.execute(cadSQL);
                try(ResultSet rs = st.getResultSet()){
                    while(rs.next()){
                        int id = rs.getInt("id_persona");
                        String nombre = rs.getString("nombre");
                        Date fechaNac = rs.getDate("fecha_nacimiento");
                        String dir = rs.getString("direccion");

                        lstPersonas.add(new PersonaDTO(id,nombre,dir,fechaNac.toLocalDate()));
                     }
                }
            }
        }
        return lstPersonas;
    }

    public List<PersonaDTO> getPersonasNacidasEntre(LocalDate fecInf, LocalDate fecSup) throws SQLException {
        if (fecInf == null)
            throw new IllegalArgumentException("Fecha Inferior no puede ser nula");
        if (fecSup ==null)
            throw new IllegalArgumentException("Fecha Superior no puede ser nula");
        if(fecInf.isAfter(fecSup))
            throw new IllegalArgumentException("La fecha inferior del rango es mayor que la superior: " + fecInf + " > " + fecSup);

        final String cadSQL = "SELECT id_persona, nombre, direccion, fecha_nacimiento FROM persona " +
                              "WHERE fecha_nacimiento between ? and ?;";
        List<PersonaDTO> lstPersonas = new ArrayList<>();

        try(Connection con = this.getConexionBD()){
            try(PreparedStatement pst = con.prepareStatement(cadSQL)){

                pst.setDate(1,Date.valueOf(fecInf));
                pst.setDate(2,Date.valueOf(fecSup));
                try(ResultSet rs = pst.executeQuery()){
                    while(rs.next()){
                        int id = rs.getInt("id_persona");
                        String nombre = rs.getString("nombre");
                        Date fechaNac = rs.getDate("fecha_nacimiento");
                        String dir = rs.getString("direccion");

                        lstPersonas.add(new PersonaDTO(id,nombre,dir,fechaNac.toLocalDate()));
                    }
                }
            }
        }
        return lstPersonas;

    }

    @Override
    public int insertarPersona(PersonaDTO persona) throws SQLException {

        if(personaInvalida(persona)){
            throw new IllegalArgumentException("registro de persona inválido " +  persona);
        }
        if(personaYaExisteEnBD(persona.getIdPersona())){
            throw new IllegalArgumentException("Registro con id duplicado: " +  persona.getIdPersona());
        }
        final String cadSQL ="INSERT INTO persona (id_persona, nombre, direccion, fecha_nacimiento) " +
                    "VALUES (?, ?, ?, ?);";
        try(Connection con = this.getConexionBD()) {
            try (PreparedStatement pst = con.prepareStatement(cadSQL)) {
                pst.setInt(1, persona.getIdPersona());
                pst.setString(2,persona.getNombre());
                pst.setString(3, persona.getDireccion());
                pst.setDate(4, Date.valueOf(persona.getFechaNacimiento()));
                int nInserts = pst.executeUpdate();
                return nInserts;
            }
        }

    }

    private boolean personaYaExisteEnBD(int id) {
        /* todo
        Checar en la BD si ese id ya existe
         */
        return false;
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

    @Override
    public PersonaDTO leerPersonaXID(int id) throws SQLException {
        final String cadSQL = "SELECT nombre, direccion, fecha_nacimiento FROM persona " +
                "where id_persona = ?;";
        try(Connection con = this.getConexionBD()){
            try(PreparedStatement pst = con.prepareStatement(cadSQL)){

                pst.setInt(1,id);
                try(ResultSet rs = pst.executeQuery()){
                    if(rs.next()){
                        Date fechaNacimiento = rs.getDate("fecha_nacimiento");
                        String nombre = rs.getString("nombre");
                        String direccion = rs.getString("direccion");
                        PersonaDTO p = new PersonaDTO(id,nombre,direccion,fechaNacimiento.toLocalDate());
//                        this.logP.guardarOperacion("LECTURA_X_ID", p);
                        return p;
                    }
                    else {
//                        this.logP.guardarOperacion("LECTURA_FALLIDA_X_ID", new Persona(id,null,null,null));
                        return null;
                    }
                }
            }
        }
    }
}
