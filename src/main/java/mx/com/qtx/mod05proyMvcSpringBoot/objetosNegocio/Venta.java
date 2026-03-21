package mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio;

import java.time.LocalDate;
import java.util.List;

public class Venta {
    private int numVta;
    private LocalDate fecha;
    private Persona cliente;
    private Persona vendedor;
    private List<DetalleVenta> detallesVta;

    public int getNumVta() {
        return numVta;
    }

    public void setNumVta(int numVta) {
        this.numVta = numVta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

    public Persona getVendedor() {
        return vendedor;
    }

    public void setVendedor(Persona vendedor) {
        this.vendedor = vendedor;
    }

    public List<DetalleVenta> getDetallesVta() {
        return detallesVta;
    }

    public void setDetallesVta(List<DetalleVenta> detallesVta) {
        this.detallesVta = detallesVta;
    }
}
