package mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos;

import java.util.Objects;

public class DetalleVentaDTO {
    private int numVenta;
    private int numDetalle;
    private int cantidad;
    private String cveArticulo;
    private Float precioUnitario;  // Usamos Float (objeto) porque la columna puede ser NULL

    public DetalleVentaDTO() {
    }

    public DetalleVentaDTO(int numVenta, int numDetalle, int cantidad, String cveArticulo, Float precioUnitario) {
        this.numVenta = numVenta;
        this.numDetalle = numDetalle;
        this.cantidad = cantidad;
        this.cveArticulo = cveArticulo;
        this.precioUnitario = precioUnitario;
    }

    // Getters y Setters
    public int getNumVenta() {
        return numVenta;
    }

    public void setNumVenta(int numVenta) {
        this.numVenta = numVenta;
    }

    public int getNumDetalle() {
        return numDetalle;
    }

    public void setNumDetalle(int numDetalle) {
        this.numDetalle = numDetalle;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCveArticulo() {
        return cveArticulo;
    }

    public void setCveArticulo(String cveArticulo) {
        this.cveArticulo = cveArticulo;
    }

    public Float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetalleVentaDTO that = (DetalleVentaDTO) o;
        return numVenta == that.numVenta &&
               numDetalle == that.numDetalle &&
               cantidad == that.cantidad &&
               Objects.equals(cveArticulo, that.cveArticulo) &&
               Objects.equals(precioUnitario, that.precioUnitario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numVenta, numDetalle, cantidad, cveArticulo, precioUnitario);
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
               "numVenta=" + numVenta +
               ", numDetalle=" + numDetalle +
               ", cantidad=" + cantidad +
               ", cveArticulo='" + cveArticulo + '\'' +
               ", precioUnitario=" + precioUnitario +
               '}';
    }
}