package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Principal;

@Component
public class ManejadorRechazoAutorizacionApi implements AccessDeniedHandler {

    private static Logger log = LoggerFactory.getLogger(ManejadorRechazoAutorizacionApi.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().append("{\"error:\"" + accessDeniedException.getMessage() + "}");

    Principal principal = request.getUserPrincipal();

      log.warn("Acceso denegado a " + request.getMethod() + " "
              + request.getRequestURI()
              + ". Peticion hecha por usuario [" + principal.getName() + "] , "
              + " con roles " + SecurityContextHolder.getContext()
                                                     .getAuthentication()
                                                     .getAuthorities());
    }

}
