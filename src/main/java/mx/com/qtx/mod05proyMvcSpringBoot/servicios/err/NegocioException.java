package mx.com.qtx.mod05proyMvcSpringBoot.servicios.err;

public class NegocioException extends RuntimeException{
    private String regla;
    private String descripcion;
    private String operacionIntentada;
    private String recomendacion;

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }

    public NegocioException(String message, Throwable cause, String regla, String descripcion, String operacionIntentada, String recomendacion) {
        super(message, cause);
        this.regla = regla;
        this.descripcion = descripcion;
        this.operacionIntentada = operacionIntentada;
        this.recomendacion = recomendacion;
    }

    public String getRegla() {
        return regla;
    }

    public void setRegla(String regla) {
        this.regla = regla;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getOperacionIntentada() {
        return operacionIntentada;
    }

    public void setOperacionIntentada(String operacionIntentada) {
        this.operacionIntentada = operacionIntentada;
    }

    public String getRecomendacion() {
        return recomendacion;
    }

    public void setRecomendacion(String recomendacion) {
        this.recomendacion = recomendacion;
    }

    @Override
    public String toString(){
        String msj = this.getClass().getSimpleName() + ": " + this.descripcion + ". " +
                "Se ha violado la regla de que " + this.regla + ". " + this.recomendacion;
        return msj;
    }
}
