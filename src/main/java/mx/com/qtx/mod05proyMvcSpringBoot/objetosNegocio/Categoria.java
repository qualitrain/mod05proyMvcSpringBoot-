package mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio;

public class Categoria {
    private String cveCategoria;
    private String descripcion;

    public Categoria() {
    }

    public Categoria(String cveCategoria, String descripcion) {
        this.cveCategoria = cveCategoria;
        this.descripcion = descripcion;
    }

    public String getCveCategoria() {
        return cveCategoria;
    }

    public void setCveCategoria(String cveCategoria) {
        this.cveCategoria = cveCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "cveCategoria='" + cveCategoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
