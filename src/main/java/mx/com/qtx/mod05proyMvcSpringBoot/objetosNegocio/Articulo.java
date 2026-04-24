package mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.ConvertGroup;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.validacion.IGrupoValidacionArticulo;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.validacion.IGrupoValidacionCategoria;

import java.time.LocalDate;

public class Articulo {
    @Size(min = 3, max = 30, message = "La clave del artículo debe tener entre 3 y 30 caracteres", groups = {IGrupoValidacionArticulo.class})
    @NotNull(message = "La clave del artículo es obligatoria", groups = {IGrupoValidacionArticulo.class})
    private String cveArticulo;

    @Size(min = 3, max = 60, message = "La descripcion del artículo debe tener entre 3 y 60 caracteres", groups = {IGrupoValidacionArticulo.class})
    @NotNull(message = "La descripcion del artículo es obligatoria", groups = {IGrupoValidacionArticulo.class})
    private String descripcion;

    @Positive(message = "El costo prov 1 debe ser mayor a cero", groups = {IGrupoValidacionArticulo.class})
    private float costoProv1;

    @Positive(message = "El precio de lista debe ser mayor a cero", groups = {IGrupoValidacionArticulo.class})
    private float precioLista;

//    private String cveCategoria;
    private boolean descontinuado;

    private LocalDate fecUltimaCompra;

    @Valid  // ← Activa validación cascada (sin groups aquí)
    private Categoria categoria;

    public String getCveArticulo() {
        return cveArticulo;
    }

    public void setCveArticulo(String cveArticulo) {
        this.cveArticulo = cveArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCostoProv1() {
        return costoProv1;
    }

    public void setCostoProv1(float costoProv1) {
        this.costoProv1 = costoProv1;
    }

    public float getPrecioLista() {
        return precioLista;
    }

    public void setPrecioLista(float precioLista) {
        this.precioLista = precioLista;
    }

//    public String getCveCategoria() {
//        return cveCategoria;
//    }
//
//    public void setCveCategoria(String cveCategoria) {
//        this.cveCategoria = cveCategoria;
//    }

    public boolean isDescontinuado() {
        return descontinuado;
    }

    public void setDescontinuado(boolean descontinuado) {
        this.descontinuado = descontinuado;
    }

    public LocalDate getFecUltimaCompra() {
        return fecUltimaCompra;
    }

    public void setFecUltimaCompra(LocalDate fecUltimaCompra) {
        this.fecUltimaCompra = fecUltimaCompra;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Articulo{" +
                "cveArticulo='" + cveArticulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costoProv1=" + costoProv1 +
                ", precioLista=" + precioLista +
                ", cveCategoria='" + ( (categoria == null) ? "" : categoria.getCveCategoria())
                + '\'' +
                ", descontinuado=" + descontinuado +
                ", fecUltimaCompra=" + fecUltimaCompra +
                '}';
    }

}
