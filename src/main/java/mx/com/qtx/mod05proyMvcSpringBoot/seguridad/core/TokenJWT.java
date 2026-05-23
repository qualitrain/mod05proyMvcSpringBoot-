package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core;

public class TokenJWT {
    private String token;

    public TokenJWT() {
    }

    public TokenJWT(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenJWT [token=" + token + "]";
    }

}