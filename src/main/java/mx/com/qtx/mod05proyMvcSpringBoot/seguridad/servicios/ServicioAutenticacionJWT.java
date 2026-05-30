package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.Autenticacion;
import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.IResultadoOperacion;
import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.IServicioAutenticacionJWT;
import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.TokenJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class ServicioAutenticacionJWT implements IServicioAutenticacionJWT {

    private static final Logger log = LoggerFactory.getLogger(ServicioAutenticacionJWT.class);
    private final AuthenticationManager autenticador;
    private final UserDetailsManager gestorBbUsuarios;
    private final IGeneradorTokensJWT generadorTokens;

    public ServicioAutenticacionJWT(AuthenticationManager autenticador, UserDetailsManager gestorBbUsuarios, IGeneradorTokensJWT generadorTokens) {
        this.autenticador = autenticador;
        this.gestorBbUsuarios = gestorBbUsuarios;
        this.generadorTokens = generadorTokens;
    }

    @Override
    public IResultadoOperacion registrarAutenticacion(Autenticacion aut) {
        IResultadoOperacion resultadoAutenticacion = new ResultadoAutenticacion();
        Authentication tokenAutenticacion = new UsernamePasswordAuthenticationToken(aut.getNombreUsuario(), aut.getPassword());
        try {
            this.autenticador.authenticate(tokenAutenticacion);
            UserDetails usuario = this.gestorBbUsuarios.loadUserByUsername(aut.getNombreUsuario());
            Collection<? extends GrantedAuthority> roles = usuario.getAuthorities();
            String tokenJwt = this.generadorTokens.generarToken(aut.getNombreUsuario(), Map.of("roles", roles));

            resultadoAutenticacion.setObjResultadoOk(new TokenJWT(tokenJwt));

            // generar resultado de exito
        }
        catch(DisabledException dex){
            // Cuenta deshabilitada
            resultadoAutenticacion.agregarError(ResultadoAutenticacion.ERR_USUARIO_INHABILITADO, dex.getMessage());
        }
        catch (LockedException lex) {
            // Cuenta bloqueda
            resultadoAutenticacion.agregarError(ResultadoAutenticacion.ERR_CTA_BLOQUEDA, lex.getMessage());
        }
        catch(BadCredentialsException bcex){
            // Credenciales equivocadas
            resultadoAutenticacion.agregarError(ResultadoAutenticacion.ERR_CREDENCIALES_EQUIVOCADAS, bcex.getMessage());
        }
        catch(Exception e){
            resultadoAutenticacion.agregarError(ResultadoAutenticacion.ERR_GENERICO, e.getMessage());

            while (e.getCause() != null) {
                e = (Exception) e.getCause();
                resultadoAutenticacion.agregarError(ResultadoAutenticacion.ERR_GENERICO, e.getClass().getName()
                        + ":" + e.getMessage());
            }
        }

        // devolver resultado
        return resultadoAutenticacion;
    }
}
