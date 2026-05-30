package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.IExtractorTokenJwtPeticionHttp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroTokensJwt_SS extends OncePerRequestFilter {

    public static int PETICION_AUTENTICADA_OK = 0;
    public static int ERROR_TOKEN_INVALIDO   = -1;
    public static int ERROR_NO_HAY_NOMBRE_USUARIO_EN_TOKEN = -2;
    public static int ERROR_YA_HAY_TOKEN_AUTENTICACION_EN_CTX_SEGURIDAD = -3;
    public static int ERROR_USUARIO_NO_ENCONTRADO = -4;

    private static final Logger log = LoggerFactory.getLogger(FiltroTokensJwt_SS.class);

    private final IExtractorTokenJwtPeticionHttp extractorTokenJwtPeticionHttp;
    private final UserDetailsService userDetailsService;

    public FiltroTokensJwt_SS(IExtractorTokenJwtPeticionHttp extractorTokenJwtPeticionHttp, UserDetailsService userDetailsService) {
        this.extractorTokenJwtPeticionHttp = extractorTokenJwtPeticionHttp;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("****************************************");
        log.info("FiltroTokensJwt_SS.doFilterInternal({}, {})", request.getServletPath(), request.getMethod());

        int resultadoAutenticacion = autenticarTokenYPublicarEnContexto(request);
        log.info("Resultado autenticacion: {}", resultadoAutenticacion);
        if (resultadoAutenticacion == PETICION_AUTENTICADA_OK) {
            filterChain.doFilter(request, response);
        }
        else {
            generarRespuestaRechazoAutenticacion(response, resultadoAutenticacion);
        }

    }

    private void generarRespuestaRechazoAutenticacion(HttpServletResponse response, int resultadoAutenticacion) throws IOException{
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.getWriter().write("{\"error\":\"Autenticacion fallida\"}");
    }

    private int autenticarTokenYPublicarEnContexto(HttpServletRequest request) {
        if (extractorTokenJwtPeticionHttp.peticionTieneTokenJWTValido(request) == false) {
            return ERROR_TOKEN_INVALIDO;
        }

        String nombreUsuario = extractorTokenJwtPeticionHttp.getNombreUsuarioSiTokenEsValido(request);
        if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            return ERROR_NO_HAY_NOMBRE_USUARIO_EN_TOKEN;
        }

        UserDetails usuario = userDetailsService.loadUserByUsername(nombreUsuario);
        if (usuario == null) {
            return ERROR_USUARIO_NO_ENCONTRADO;
        }

        SecurityContext ctxSeguridad = SecurityContextHolder.getContext();
        if(ctxSeguridad.getAuthentication() != null) {
            return ERROR_YA_HAY_TOKEN_AUTENTICACION_EN_CTX_SEGURIDAD;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,
                usuario.getPassword(), usuario.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        ctxSeguridad.setAuthentication(authentication);
        return PETICION_AUTENTICADA_OK;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getServletPath().equals("/api/autenticar")) {
            return true;
        }
        if (request.getServletPath().startsWith("/api")) {
            return false;
        }
        return true;
    }
}
