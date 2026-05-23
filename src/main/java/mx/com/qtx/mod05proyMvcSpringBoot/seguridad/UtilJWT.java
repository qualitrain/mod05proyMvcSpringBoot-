package mx.com.qtx.mod05proyMvcSpringBoot.seguridad;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;


public class UtilJWT {
    private static final long duracionTokens = 1000 * 60 * 60;
    private static final String EMISOR = "www.qtx.com";
    private static final SecretKey llave = getLLave();

    private static SecretKey getLLave() {
        UUID id = UUID.randomUUID();
        return Keys.hmacShaKeyFor(id.toString().getBytes());
    }

    public static void main(String[] args) {
//        test_generacionLlave();
        UtilJWT generadorTokens =new UtilJWT();
        String token = generadorTokens.generarToken("Edgar");
        System.out.println("token = " + token);
    }

    private static void test_generacionLlave() {
        String secreto = "Me parece que he visto un lindo gatioo. Es cierto, he visto unlido gatito";

        UUID id = UUID.randomUUID();
        System.out.println("id.toString() = " + id.toString());
        SecretKey llave = Keys.hmacShaKeyFor(id.toString().getBytes());
        System.out.println("llave = " + llave.toString());
    }

    public String generarToken(String nombreUsuario) {
        Date ahora      = new Date();
        Date expiracion = new Date(System.currentTimeMillis()
                + duracionTokens);
        String id = UUID.randomUUID().toString()
                .replace("-", "");

        return Jwts.builder()
                .id(id)
                .issuer(EMISOR)
                .subject(nombreUsuario)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(llave)
                .compact();
    }

}
