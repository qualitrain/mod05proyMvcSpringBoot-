package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ErrorRest {
    public static final int ERR_RECURSO_INEXISTENTE = 9000;
    public static final int ERR_AUTENTICACION_FALLIDA = 9001;

    private String descripcion;
    private int codigoError;

    public ErrorRest(String descripcion, int codigoError) {
        super();
        this.descripcion = descripcion;
        this.codigoError = codigoError;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCodigoError() {
        return codigoError;
    }

    public void setCodigoError(int codigoError) {
        this.codigoError = codigoError;
    }

    @Override
    public String toString() {
        return "ErrorRest [descripcion=" + descripcion + ", codigoError=" + codigoError + "]";
    }

    public static ResponseEntity<ErrorRest> getError(String causa, int codError, HttpStatus status) {
        return ResponseEntity.status(status).body(new ErrorRest(causa, codError));
    }

}