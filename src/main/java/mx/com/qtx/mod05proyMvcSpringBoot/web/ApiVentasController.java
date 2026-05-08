package mx.com.qtx.mod05proyMvcSpringBoot.web;

import mx.com.qtx.mod05proyMvcSpringBoot.core.IGestorVentas;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Categoria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ApiVentasController {

    final private static Logger log = LoggerFactory.getLogger(ApiVentasController.class);
    private final IGestorVentas gestorVtas;

    public ApiVentasController(IGestorVentas gestorVtas) {
        this.gestorVtas = gestorVtas;
    }

    @GetMapping(path = "/api/articulos/{cveArticulo}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Articulo getArticuloXcve(@PathVariable String cveArticulo){
        log.info("getArticuloXcve({})",cveArticulo);
        Articulo articulo = gestorVtas.recuperarArticuloXID(cveArticulo);
        return articulo;
    }

    @GetMapping(path = "/api/articulos", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Articulo> getArticulosConSinCategoria(
            @RequestParam(name = "inccat", required = false, defaultValue = "false")
            boolean incluirCategorias){
        if(incluirCategorias)
            return this.gestorVtas.recuperarArticulos();
        else {
            return this.gestorVtas.recuperarArticulosSinCategorias();
        }

    }

    @GetMapping(path = "/api/categorias/{cveCategoria}")
    public ResponseEntity<Categoria> getCategoriaXcve(@PathVariable String cveCategoria){
        Categoria cat = this.gestorVtas.recuperarCategoriaXID(cveCategoria);

        ResponseEntity<Categoria> respuesta = ResponseEntity.status(201)
                                                            .header("FOLIO","5801")
                                                            .body(cat);
        return respuesta;
    }

    @GetMapping(path="/api/categorias")
//    public ResponseEntity<List<Categoria>> getCategporias(){
    public ResponseEntity<Object> getCategporias(){
        List<Categoria> lstCategorias = this.gestorVtas.recuperarCategorias();
        ResponseEntity<Object> respuesta = ResponseEntity.ok().header("SIZE","" +lstCategorias.size())
                .body(lstCategorias);
        return respuesta;
    }


}
