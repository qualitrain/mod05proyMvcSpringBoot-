package mx.com.qtx.mod05proyMvcSpringBoot.web;

import jakarta.servlet.http.HttpServletRequest;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.IGestorVentas;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.Locale;

@Controller
public class VentasController {
    final private static Logger log = LoggerFactory.getLogger(VentasController.class);
    private final IGestorVentas gestorVtas;

    @Autowired
    MessageSource ms;

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

        final String msgArtNoEspec = getMensajeI18n("servicio.articulos.error.artNoEspecificado");
        final String msgArtNoExiste = getMensajeI18n("servicio.articulos.error.artNoExiste");
        if(cveArticulo == null){
            model.addAttribute("mensaje",msgArtNoEspec);
            model.addAttribute("articulo", getArticuloVacio());
            return "consultaArticulo";
        }
        if(cveArticulo.trim().isEmpty()){
            model.addAttribute("mensaje",msgArtNoEspec);
            return "consultaArticulo";
        }

        Articulo art = this.gestorVtas.getArticuloXID(cveArticulo);
        if(art == null){
            model.addAttribute("mensaje",msgArtNoExiste);
            return "consultaArticulo";
        }

        model.addAttribute("articulo",art);
        log.info(art.toString());
        return "consultaArticulo";
    }

    private String getMensajeI18n(String llaveMsg) {
        Locale localidadMsgs = LocaleContextHolder.getLocale();
        String msgArtNoEspec = ms.getMessage(llaveMsg,null,localidadMsgs);
        return msgArtNoEspec;
    }

    private Articulo  getArticuloVacio() {
        Articulo artVacio = new Articulo();
        artVacio.setCveArticulo("");
        artVacio.setDescripcion("");
        artVacio.setPrecioLista(0);
        artVacio.setCostoProv1(0);
        return artVacio;
    }
}
