package mx.com.qtx.mod05proyMvcSpringBoot.servicios.err;

public class InsercionDuplicadaException extends NegocioException{
    public InsercionDuplicadaException(String message) {
        super(message, null);
    }

    public InsercionDuplicadaException(String message, Throwable cause, String regla, String descripcion, String operacionIntentada, String recomendacion) {
        super(message, cause, regla, descripcion, operacionIntentada, recomendacion);
    }
}
