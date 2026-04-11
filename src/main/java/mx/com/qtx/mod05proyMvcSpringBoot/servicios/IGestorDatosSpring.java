package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.*;

import java.util.List;

public interface IGestorDatosSpring {
    PersonaDTO leerPersonaXID(int idPersona);
    int insertarPersona(PersonaDTO persona);

    VentaDTO leerVentaXID(int numVta);
    VentaDTO insertarVenta(VentaDTO vta);

    List<DetalleVentaDTO> leerDetallesVenta(int numVta);
    int insertarDetalleVenta(DetalleVentaDTO detVta);

    ArticuloDTO leerArticuloXID(String cveArt);
    int insertarArticulo(ArticuloDTO articulo);
    int actualizarArticulo(ArticuloDTO articulo);
    int eliminarArticulo(String cveArticulo);
    // Listar todos los artículos
    List<ArticuloDTO> leerArticulos();
    // Listar artículos por categoría
    List<ArticuloDTO> leerArticulosPorCategoria(String cveCategoria);

    // ==================== Categoría (nuevo CRUD) ====================
    // Leer categoría por ID
    CategoriaDTO leerCategoriaXID(String cveCategoria);
    // Insertar categoría
    int insertarCategoria(CategoriaDTO categoria);
    // Actualizar categoría
    int actualizarCategoria(CategoriaDTO categoria);
    // Eliminar categoría
    int eliminarCategoria(String cveCategoria);
    // Listar todas las categorías
    List<CategoriaDTO> leerCategorias();

    Integer exportarDatos(String sql);
}
