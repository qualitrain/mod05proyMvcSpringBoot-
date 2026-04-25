package mx.com.qtx.mod05proyMvcSpringBoot.web;

import mx.com.qtx.mod05proyMvcSpringBoot.core.IGestorVentas;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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

    //Pendiente
    public List<Articulo> getArticulos(){
        return null;
    }
}
