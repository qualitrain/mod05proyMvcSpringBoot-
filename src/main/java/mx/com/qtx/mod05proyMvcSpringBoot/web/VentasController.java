package mx.com.qtx.mod05proyMvcSpringBoot.web;

import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/consultarArticulo")
    public String irAconsulaArticulo(HttpServletRequest req){
        log.info("irAconsulaArticulo()");
        return "consultaArticulo";
    }

    @GetMapping("/buscarArticulos")
    public String buscarArticulo(@RequestParam(name = "precioLista",defaultValue = "1.0") BigDecimal precioLista,
                                 String cveArticulo, String descripcion,
                                 @RequestParam(defaultValue = "0.0") BigDecimal costoProv1, Model model){
        log.info("buscarArticulo({},{},{},{})", cveArticulo, descripcion, costoProv1, precioLista);

        model.addAttribute("mensaje","Metodo no disponible. Lo siento mucho");

        return "consultaArticulo";
    }
}
