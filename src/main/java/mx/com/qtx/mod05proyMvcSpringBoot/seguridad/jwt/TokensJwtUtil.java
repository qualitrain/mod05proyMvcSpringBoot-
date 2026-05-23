package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

import javax.crypto.SecretKey;

import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.servicios.IGeneradorTokensJWT;
import org.slf4j.Logger;
import java.util.Map;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TokensJwtUtil implements IGeneradorTokensJWT {
    private static final Logger log = LoggerFactory.getLogger(TokensJwtUtil.class);

    private static final String ISSUER = "mx.com.qtx";
    private static final String SECRET = UUID.randomUUID().toString();
    private static final long EXPIRATION_TIME = 86400000; // 24 horas
    private static final long EXPIRATION_TIME_TEST = 100; // 100 milisegundos
    private static SecretKey llave = getLlave();

    public static String generarToken(String username, long tiempoExpiracion) {

        // secretKeyFor(SignatureAlgorithm.HS512);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(ISSUER)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
                .signWith(llave)
                .compact();
    }

    private static SecretKey getLlave() {
        SecretKey llave = Keys.hmacShaKeyFor(SECRET.getBytes());
        return llave;
    }

    // @Override
    public void run(String... args) throws Exception {
        String token = generarToken("alex", EXPIRATION_TIME);
        log.info("Token: " + token);

        SecretKey llave = getLlave();

        Map<String, Object> claims = new HashMap<>();
        claims.put("usuario", "humberto");
        claims.put("role", "ADMIN");

        try {
            String token2 = getTokenConClaims("beto", claims, llave, EXPIRATION_TIME);
            log.info("Token2: " + token2);

            // Thread.sleep(EXPIRATION_TIME_TEST + 100);

            Jws<Claims> jwsClaims = extraerJwsClaimsTokenFirmado(token2, llave);
            log.info("JwsClaims: " + jwsClaims);

            log.info("Contenido Token: " + extraerContenidoTokenFirmadoStr(token2, llave));

            log.info("Usuario Token: " + extraerUsuarioTokenFirmado(token2, llave));
            log.info("Expiracion Token: " + extraerExpiracionTokenFirmado(token2, llave));
            log.info("Rol Token: " + extraerCampoString(token2, llave, "role"));
            log.info("Usuario Token: " + extraerCampoString(token2, llave, "usuario"));

        } catch (ExpiredJwtException e) {
            log.error("Error al generar o extraer token: " + e.getMessage());
        } catch (Exception e) {
            log.error("Error al generar o extraer token: " + e.getMessage());
        }

    }

    private String getTokenConClaims(String username, Map<String, Object> claims, Key llave, long tiempoExpiracion) {
        Claims declaraciones = Jwts.claims()
                .add(claims)
                .build();

        String token2 = Jwts.builder()
                .issuer(ISSUER)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tiempoExpiracion))
                .claims(declaraciones)
                .signWith(llave)
                .compact();
        return token2;
    }

    private Jws<Claims> extraerJwsClaimsTokenFirmado(String tokenFirmado, SecretKey skLlave) {
        Jws<Claims> jwsClaims = Jwts.parser().verifyWith(skLlave)
                .build()
                .parseSignedClaims(tokenFirmado);
        return jwsClaims;
    }

    public String extraerContenidoTokenFirmadoStr(String tokenFirmado, SecretKey skLlave) {
        Jws<Claims> jws = this.extraerJwsClaimsTokenFirmado(tokenFirmado, skLlave);
        return jws.getHeader() + " " + jws.getPayload() + " "
                + io.jsonwebtoken.io.Encoders.BASE64URL.encode(jws.getDigest());
    }

    public String extraerUsuarioTokenFirmado(String token, SecretKey skLlave) {
        Jws<Claims> contenido = this.extraerJwsClaimsTokenFirmado(token, skLlave);
        return contenido.getPayload().getSubject();
    }

    public Date extraerExpiracionTokenFirmado(String token, SecretKey skLlave) {
        Jws<Claims> contenido = this.extraerJwsClaimsTokenFirmado(token, skLlave);
        return contenido.getPayload().getExpiration();
    }

    public String extraerCampoString(String tokenFirmado, SecretKey skLlave, String llaveCampo) {
        Jws<Claims> contenido = this.extraerJwsClaimsTokenFirmado(tokenFirmado, skLlave);
        Claims claims = contenido.getPayload();
        return (String) claims.get(llaveCampo);
    }

    @Override
    public String generarToken(String nombreUsuario) {
        return generarToken(nombreUsuario, EXPIRATION_TIME);
    }

    @Override
    public String generarToken(String nombreUsuario, Map<String, Object> mapClaims) {
        return generarToken(nombreUsuario, mapClaims, EXPIRATION_TIME);
    }

    @Override
    public String generarToken(String nombreUsuario, Map<String, Object> mapClaims, long milisDuracion) {
        if (mapClaims == null) {
            mapClaims = new HashMap<>();
        }
        return getTokenConClaims(nombreUsuario, mapClaims, getLlave(), milisDuracion);
    }

    @Override
    public String extraerUsuario(String token) {
        try {
            return extraerUsuarioTokenFirmado(token, getLlave());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean tokenExpirado(String token) {
        try {
            Date expiracion = extraerExpiracionTokenFirmado(token, llave);
            return expiracion.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return true; // Ante cualquier error que impida extraer la expiración asumimos true/inválido
        }
    }

    @Override
    public boolean tokenValido(String tokenFirmado, String nombreUsuario) {
        try {
            String usuario = extraerUsuarioTokenFirmado(tokenFirmado, llave);
            return usuario.equals(nombreUsuario) && !tokenExpirado(tokenFirmado);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getLlaveBase64() {
        return java.util.Base64.getEncoder().encodeToString(llave.getEncoded());
    }

}