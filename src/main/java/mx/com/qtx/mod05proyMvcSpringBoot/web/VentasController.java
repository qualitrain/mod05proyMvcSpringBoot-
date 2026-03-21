package mx.com.qtx.mod05proyMvcSpringBoot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;

@Controller
public class VentasController {
    final private static Logger log = LoggerFactory.getLogger(VentasController.class);

    @GetMapping("/consultarArticulo")
    public String irAconsulaArticulo(){
        log.info("irAconsulaArticulo()");
        return "consultaArticulo";
    }

    @GetMapping("/buscarArticulos")
    public String buscarArticulo(String cveArticulo, String descripcion, BigDecimal costoProv1, BigDecimal precioLista){
        log.info("buscarArticulo({},{},{},{})", cveArticulo, descripcion, costoProv1, precioLista);
        return "consultaArticulo";
    }
}
