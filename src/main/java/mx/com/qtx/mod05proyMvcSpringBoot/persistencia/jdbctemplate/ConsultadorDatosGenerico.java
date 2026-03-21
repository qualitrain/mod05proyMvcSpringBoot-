package mx.com.qtx.mod05proyMvcSpringBoot.persistencia.jdbctemplate;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultadorDatosGenerico implements ResultSetExtractor<Integer> {
    private static final Logger log = LoggerFactory.getLogger(ConsultadorDatosGenerico.class);

    @Override
    public @Nullable Integer extractData(ResultSet rs) throws SQLException, DataAccessException {
        explorarResultSet(rs);
        int nCols = rs.getMetaData().getColumnCount();
        rs.next();
        do {
            log.info("--------------------------------");
            for(int iCol=1; iCol<=nCols; iCol++){
                Object valColI = rs.getObject(iCol);
                log.info("    {}={}",rs.getMetaData().getColumnName(iCol), valColI.toString());
            }


        }while(rs.next());
        return null;
    }

    private static void explorarResultSet(ResultSet rs) throws SQLException {
        int nCols = rs.getMetaData().getColumnCount();
        log.info("El ResultSet tiene {} columnas",nCols);
        for(int i=1;i<=nCols;i++){
            String esquema = rs.getMetaData().getCatalogName(i);
            String tipoJava = rs.getMetaData().getColumnClassName(i);
            String label = rs.getMetaData().getColumnLabel(i);
            String nombreCol = rs.getMetaData().getColumnName(i);
            String typeName = rs.getMetaData().getColumnTypeName(i);
            String tabla = rs.getMetaData().getTableName(i);
            log.info("    Nombre columna:{}",nombreCol);
            log.info("    Esquema:{}",esquema);
            log.info("    Tabla:{}",tabla);
            log.info("    Tipo Java;{}",tipoJava);
            log.info("    Label:{}",label);
            log.info("    Tipo en BD:{}", typeName);
            log.info("    ---------------------------- ");
        }
    }
}
