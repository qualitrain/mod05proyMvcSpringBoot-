package mx.com.qtx.mod05proyMvcSpringBoot.seguridad;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Deprecated
public class UtilJWT {
    private static final long duracionTokens_1HORA = 1000 * 60 * 60;
    private  static final long duracionTokens = 5;
    private static final String EMISOR = "www.qtx.com";
    private static final SecretKey llave = getLLave();

    private static SecretKey getLLave() {
        UUID id = UUID.randomUUID();
        return Keys.hmacShaKeyFor(id.toString().getBytes());
    }

    public static void main(String[] args) {
//        test_generacionLlave();
//        test_generacionToken();
//        test_generacionToken_conClaims();

        final String  tokenVini = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0NTZjYzJkMzg4ZTE0ZTUzYmVhMTA3" +
                "OGI0NWM3NDAzZSIsImlzcyI6Ind3dy5xdHguY" +
                "29tIiwic3ViIjoiVmluaSIsInJvbGVzIjoiVnRhcyxjb21wcmFzIiwiamVmZSI6IkZ1bGFuaXRvIGRlIHRhbCIsIm" +
                "lhdCI6MTc3OTQ5ODYzOCwiZXhwIjoxNzc5NTAyMjM4fQ.n0LkudMRwUXLFpZBdKMh-ktmiX5V7Q8igpXS2TdXtBI";
        UtilJWT generadorTokens =new UtilJWT();

        final String secretPermanante = "Me parece que he visto un lindo gatito. " +
                "Es cierto, es cierto. He visto un lindo gatito";

        final SecretKey llavePermanente = Keys.hmacShaKeyFor(secretPermanante.getBytes());

        String tokenSilvia = generadorTokens.generarToken("Ana Silvia",llavePermanente);
        System.out.println("tokenSilvia = " + tokenSilvia);

        hacerPausa();

        JwtParser lectorTokenP = Jwts.parser().verifyWith(llavePermanente).build();
        JwtParser lectorToken = Jwts.parser().verifyWith(llave).build();

        try {
            Jws<Claims> declaraciones = lectorTokenP.parseSignedClaims(tokenSilvia);
            JwsHeader header = declaraciones.getHeader();
            Claims payload = declaraciones.getPayload();
            byte[] digest = declaraciones.getDigest();

            System.out.println("\nHeader del token JWT:");
            header.forEach((k, v) -> System.out.println("k + \", \" + v.toString() = " + k + ", " + v.toString()));


            System.out.println("\nPayload del token JWT:");
            payload.forEach((k, v) -> System.out.println("k + \", \" + v.toString() = " + k + ", " + v.toString()));
        }
        catch(ExpiredJwtException ekex){
            System.out.println("El token ha expirado");
        }

        try {
            Jws<Claims> claimsJwsVini = lectorToken.parseSignedClaims(tokenVini);
        }
        catch(SignatureException siex){
            System.out.println("El token no corresponde con la firma");
        }
    }

    private static void hacerPausa() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void test_generacionToken_conClaims() {
        Map<String,Object> declaraciones = new HashMap<>();
        declaraciones.put("roles", "Vtas,compras");
        declaraciones.put("jefe","Fulanito de tal");

        UtilJWT generadorTokens =new UtilJWT();
        String token = generadorTokens.generarToken("Vini",declaraciones);
        System.out.println("token = " + token);
    }

    private static void test_generacionToken() {
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

    public String generarToken(String nombreUsuario, SecretKey llaveParam) {
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
                .signWith(llaveParam)
                .compact();
    }

    public String generarToken(String nombreUsuario) {
        return this.generarToken(nombreUsuario,llave);
    }

    public String generarToken(String nombreUsuario, Map<String,Object> declaraciones) {

        Claims claims = Jwts.claims().add(declaraciones)
                                    .build();

        Date ahora      = new Date();
        Date expiracion = new Date(System.currentTimeMillis()
                + duracionTokens);
        String id = UUID.randomUUID().toString()
                .replace("-", "");

        return Jwts.builder()
                .id(id)
                .issuer(EMISOR)
                .subject(nombreUsuario)
                .claims(claims)
                .issuedAt(ahora)
                .expiration(expiracion)
                .signWith(llave)
                .compact();
    }

}
