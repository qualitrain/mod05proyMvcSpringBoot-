package mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos;

import java.time.LocalDate;
import java.util.Objects;

public class VentaDTO {
    private int numVenta;          // AUTO_INCREMENT, se genera en la BD
    private LocalDate fechaVenta;  // java.time.LocalDate para la columna DATE
    private int idPersonaCte;
    private Integer idPersonaVendedor;  // Puede ser NULL

    public VentaDTO() {
    }

    public VentaDTO(int numVenta, LocalDate fechaVenta, int idPersonaCte, Integer idPersonaVendedor) {
        this.numVenta = numVenta;
        this.fechaVenta = fechaVenta;
        this.idPersonaCte = idPersonaCte;
        this.idPersonaVendedor = idPersonaVendedor;
    }

    // Getters y Setters
    public int getNumVenta() {
        return numVenta;
    }

    public void setNumVenta(int numVenta) {
        this.numVenta = numVenta;
    }

    public LocalDate getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(LocalDate fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public int getIdPersonaCte() {
        return idPersonaCte;
    }

    public void setIdPersonaCte(int idPersonaCte) {
        this.idPersonaCte = idPersonaCte;
    }

    public Integer getIdPersonaVendedor() {
        return idPersonaVendedor;
    }

    public void setIdPersonaVendedor(Integer idPersonaVendedor) {
        this.idPersonaVendedor = idPersonaVendedor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VentaDTO venta = (VentaDTO) o;
        return numVenta == venta.numVenta &&
               idPersonaCte == venta.idPersonaCte &&
               Objects.equals(fechaVenta, venta.fechaVenta) &&
               Objects.equals(idPersonaVendedor, venta.idPersonaVendedor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numVenta, fechaVenta, idPersonaCte, idPersonaVendedor);
    }

    @Override
    public String toString() {
        return "Venta{" +
               "numVenta=" + numVenta +
               ", fechaVenta=" + fechaVenta +
               ", idPersonaCte=" + idPersonaCte +
               ", idPersonaVendedor=" + idPersonaVendedor +
               '}';
    }
}