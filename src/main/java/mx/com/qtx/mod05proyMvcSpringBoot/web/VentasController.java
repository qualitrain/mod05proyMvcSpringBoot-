package mx.com.qtx.mod05proyMvcSpringBoot.web;

import jakarta.servlet.http.HttpServletRequest;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.IGestorVentas;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@Controller
public class VentasController {
    final private static Logger log = LoggerFactory.getLogger(VentasController.class);
    private final IGestorVentas gestorVtas;

    public VentasController(IGestorVentas gestorVtas) {
        this.gestorVtas = gestorVtas;
    }

    @GetMapping("/consultarArticulo")
    public String irAconsulaArticulo(HttpServletRequest req){
        log.info("irAconsulaArticulo()");
        return "consultaArticulo";
    }

    @GetMapping("/buscarArticulos")
    public String buscarArticulo(String cveArticulo, Model model){

        if(cveArticulo == null){
            model.addAttribute("mensaje","Articulo no especificado");
            return "consultaArticulo";
        }
        if(cveArticulo.trim().isEmpty()){
            model.addAttribute("mensaje","Articulo no especificado");
            return "consultaArticulo";
        }

        Articulo art = this.gestorVtas.getArticuloXID(cveArticulo);
        if(art == null){
            model.addAttribute("mensaje","Articulo no encontrado");
            return "consultaArticulo";
        }

        model.addAttribute("articulo",art);
        log.info(art.toString());
        return "consultaArticulo";
    }
}
