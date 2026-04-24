package mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.validacion.IGrupoValidacionArticulo;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.validacion.IGrupoValidacionCategoria;

public class Categoria {

    @NotNull(message = "La clave de la categoria es obligatoria",
            groups = {IGrupoValidacionArticulo.class, IGrupoValidacionCategoria.class})
    @Size(min = 2, max = 6, message = "La clave de la categoría debe tener entre 2 y 6 caracteres",
            groups = {IGrupoValidacionArticulo.class, IGrupoValidacionCategoria.class})
    private String cveCategoria;

    @Size(min = 5, max = 60, message = "La descripcion de la categoria debe tener entre 5 y 60 caracteres",
            groups = {IGrupoValidacionCategoria.class})
    @NotNull(message = "La descripcion de la categoria es obligatoria",
            groups = {IGrupoValidacionCategoria.class})
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
