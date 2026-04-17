package mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio;

import java.time.LocalDate;

public class Articulo {
    private String cveArticulo;
    private String descripcion;
    private float costoProv1;
    private float precioLista;
//    private String cveCategoria;
    private boolean descontinuado;
    private LocalDate fecUltimaCompra;

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
