package mx.com.qtx.mod05proyMvcSpringBoot.core;

import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Categoria;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Venta;

import java.util.List;
import java.util.Map;

public interface IGestorVentas {
    Venta recuperarVenta(int numVta);
    int registrarVenta(Venta vta);
    Articulo recuperarArticuloXID(String cveArt);

    List<Categoria> recuperarCategorias();

    Map<String,String> recuperarMapCategoriasAlf();
}
