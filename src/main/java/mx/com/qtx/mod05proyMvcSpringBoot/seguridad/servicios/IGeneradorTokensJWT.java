package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.servicios;

import java.sql.Date;
import java.util.Map;

public interface IGeneradorTokensJWT {

    String generarToken(String nombreUsuario);

    String generarToken(String nombreUsuario, Map<String, Object> mapClaims);

    String generarToken(String nombreUsuario, Map<String, Object> mapClaims, long milisDuracion);

    String extraerUsuario(String token);

    boolean tokenExpirado(String token);

    boolean tokenValido(String tokenFirmado, String nombreUsuario);

    String getLlaveBase64();

}