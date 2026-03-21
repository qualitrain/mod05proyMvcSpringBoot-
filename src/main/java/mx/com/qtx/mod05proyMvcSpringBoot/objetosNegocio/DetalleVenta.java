package mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio;

import java.math.BigDecimal;

public class DetalleVenta {
    private int numDetalle;
    private Articulo articulo;
    private int catidad;
    private BigDecimal precio;

    public DetalleVenta() {
    }

    public DetalleVenta(int numDetalle, Articulo articulo, int catidad, BigDecimal precio) {
        this.numDetalle = numDetalle;
        this.articulo = articulo;
        this.catidad = catidad;
        this.precio = precio;
    }

    public int getNumDetalle() {
        return numDetalle;
    }

    public void setNumDetalle(int numDetalle) {
        this.numDetalle = numDetalle;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public int getCatidad() {
        return catidad;
    }

    public void setCatidad(int catidad) {
        this.catidad = catidad;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }
}
