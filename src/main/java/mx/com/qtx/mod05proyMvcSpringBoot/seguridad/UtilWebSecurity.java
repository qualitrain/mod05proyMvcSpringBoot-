package mx.com.qtx.mod05proyMvcSpringBoot.seguridad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class UtilWebSecurity {
    private static final Logger log = LoggerFactory.getLogger(UtilWebSecurity.class);

    public static String getPrincipalConRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null) {
            log.warn("El principal es nulo");
            return "nulo";
        }
        if(auth.isAuthenticated() == false) {
            log.warn("Peticion de principal no autenticado " + auth.getName());
            return auth.getName() + auth.getAuthorities();
        }
        log.debug("Peticion de principal autenticado");
        if(auth instanceof AnonymousAuthenticationToken) {
            log.debug("Peticion de principal anónimo " + auth.getName());
            return "invitado"  + auth.getAuthorities();
        }
        log.debug("Autenticación de tipo " + auth.getClass().getName());
        return auth.getName()  + auth.getAuthorities();
    }
}
