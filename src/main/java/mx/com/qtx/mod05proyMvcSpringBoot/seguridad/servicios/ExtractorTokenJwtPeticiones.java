package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.servicios;

import jakarta.servlet.http.HttpServletRequest;
import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.IExtractorTokenJwtPeticionHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExtractorTokenJwtPeticiones implements IExtractorTokenJwtPeticionHttp {
    private static final Logger log = LoggerFactory.getLogger(ExtractorTokenJwtPeticiones.class);

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    private final IGeneradorTokensJWT analizadorTokenJWT;

    public ExtractorTokenJwtPeticiones(IGeneradorTokensJWT analizadorTokenJWT) {
        this.analizadorTokenJWT = analizadorTokenJWT;
    }

    @Override
    public boolean peticionTieneTokenJWTValido(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION);
        if(authorizationHeader == null)
            return false;
        if(authorizationHeader.startsWith(BEARER_PREFIX) == false)
            return false;
        String tokenJWT = authorizationHeader.substring(7);
        if(tokenJWT.isEmpty())
            return false;
        try {
            if(analizadorTokenJWT.tokenExpirado(tokenJWT))
                return false;
        }
        catch(Exception ex) {
            return false;
        }
        return true;
    }

    @Override
    public String getNombreUsuarioSiTokenEsValido(HttpServletRequest request) {
        if (peticionTieneTokenJWTValido(request) == false) {
            return null;
        }
        String token = request.getHeader(HEADER_AUTHORIZATION);
        token = token.substring(BEARER_PREFIX.length());
        return this.analizadorTokenJWT.extraerUsuario(token);
    }
}
