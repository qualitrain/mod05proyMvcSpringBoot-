package mx.com.qtx.mod05proyMvcSpringBoot.web;

public class ErrorCte {
    private String descripcion;

    public ErrorCte(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "ErrorCte{" +
                "descripcion='" + descripcion + '\'' +
                '}';
    }
}
