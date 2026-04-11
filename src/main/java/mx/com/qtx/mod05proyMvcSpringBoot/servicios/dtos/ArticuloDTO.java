package mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos;

import java.time.LocalDate;
import java.util.Objects;

public class ArticuloDTO {
    private String cveArticulo;
    private String descripcion;
    private float costoProv1;
    private float precioLista;
    private String cveCategoria;
    private boolean descontinuado;
    private LocalDate fecUltimaCompra;

    public ArticuloDTO() {
    }

    public ArticuloDTO(String cveArticulo, String descripcion, float costoProv1, float precioLista) {
        this.cveArticulo = cveArticulo;
        this.descripcion = descripcion;
        this.costoProv1 = costoProv1;
        this.precioLista = precioLista;
    }

    // Getters y Setters
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

    public String getCveCategoria() {
        return cveCategoria;
    }

    public void setCveCategoria(String cveCategoria) {
        this.cveCategoria = cveCategoria;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ArticuloDTO articulo = (ArticuloDTO) o;
        return Float.compare(articulo.costoProv1, costoProv1) == 0 &&
               Float.compare(articulo.precioLista, precioLista) == 0 &&
               Objects.equals(cveArticulo, articulo.cveArticulo) &&
               Objects.equals(descripcion, articulo.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cveArticulo, descripcion, costoProv1, precioLista);
    }

    @Override
    public String toString() {
        return "ArticuloDTO{" +
                "cveArticulo='" + cveArticulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", costoProv1=" + costoProv1 +
                ", precioLista=" + precioLista +
                ", cveCategoria='" + cveCategoria + '\'' +
                ", descontinuado=" + descontinuado +
                ", fecUltimaCompra=" + fecUltimaCompra +
                '}';
    }
}