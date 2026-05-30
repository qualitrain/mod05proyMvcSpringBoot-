package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.web;

import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.Autenticacion;
import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.IResultadoOperacion;
import mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core.IServicioAutenticacionJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiAutenticacion {
    private static final Logger log = LoggerFactory.getLogger(ApiAutenticacion.class);
    private final IServicioAutenticacionJWT servicioAutenticacion;

    public ApiAutenticacion(IServicioAutenticacionJWT servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @PostMapping(value="/api/autenticar", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> autenticar(@RequestBody Autenticacion objAutenticacion){
        log.info("ApiAutenticacion.autenticar({})", objAutenticacion.toString());
        IResultadoOperacion resultado = this.servicioAutenticacion.registrarAutenticacion(objAutenticacion);
        if(resultado.todoOk()){
            log.info("TokenJWT:{} {}", resultado.getObjResultadoOk().getClass().getName(),resultado.getObjResultadoOk());
            return ResponseEntity.ok(resultado.getObjResultadoOk());
        }
        else{
            log.error("Error al autenticar: {}", resultado.getResumenErrores());
            return ErrorRest.getError(resultado.getResumenErrores(), ErrorRest.ERR_AUTENTICACION_FALLIDA,
                    HttpStatus.UNAUTHORIZED);
        }
    }
}
