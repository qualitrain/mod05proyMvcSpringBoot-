package mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos;

public class CategoriaDTO
{
    private String cveCategoria;
    private String descripcion;

    public CategoriaDTO(String cveCategoria, String descripcion) {
        this.cveCategoria = cveCategoria;
        this.descripcion = descripcion;
    }

    public String getCveCategoria() {
        return cveCategoria;
    }

    public void setCveCategoria(String cveCategoria) {
        this.cveCategoria = cveCategoria;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "cveCategoria='" + cveCategoria + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}