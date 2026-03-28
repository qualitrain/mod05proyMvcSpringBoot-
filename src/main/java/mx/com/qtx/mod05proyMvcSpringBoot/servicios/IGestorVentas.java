package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Venta;

public interface IGestorVentas {
    Venta recuperarVenta(int numVta);
    int registrarVenta(Venta vta);
    Articulo getArticuloXID(String cveArt);
}
