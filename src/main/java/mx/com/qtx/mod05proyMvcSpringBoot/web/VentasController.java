package mx.com.qtx.mod05proyMvcSpringBoot.web;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.core.IGestorVentas;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.validacion.IGrupoValidacionArticulo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
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
    public String irAconsultaArticulo(HttpServletRequest req){
        log.info("irAconsultaArticulo()");
        return "consultaArticulo";
    }

    @GetMapping("/insertarArticulo")
    public String irAinsercionArticulo(Model modelo){
        log.info("irAinsercionArticulo()");
        Articulo art = getArticuloVacio();
        modelo.addAttribute("articulo",art);
        modelo.addAttribute("categoriasMap", this.gestorVtas.recuperarMapCategoriasAlf());
        return "updateArticulo";
    }

    @PostMapping("/procesarInsercionArticulo")
    public String procesarInsercionArticulo(@Validated(IGrupoValidacionArticulo.class) Articulo art,
                                            BindingResult resulValidacion,
                                            Model model){
        log.info("procesarInsercionArticulo({})",art);

        if(resulValidacion.hasErrors()){
            int nErrores = resulValidacion.getErrorCount();
            mostrarErroresValidacion(resulValidacion, nErrores);
            model.addAttribute("estadoValidacion","Hay " + nErrores + " errores");
            return "consultaArticulo";
        }

        this.gestorVtas.insertarArticulo(art);

        return "consultaArticulo";
    }

    private static void mostrarErroresValidacion(BindingResult resulValidacion, int nErrores) {
        log.error("Se encontraron {} errores en la Validación del artículo", nErrores);

        resulValidacion.getAllErrors()
                        .forEach(errI->log.error("Restriccion no cubierta: {}, mensaje: {}",
                                errI.getCode(),
                                errI.getDefaultMessage())
                        );

        resulValidacion.getFieldErrors()
                .forEach(fErrI->log.error("campo:{}, codigo:{}, msj:{}, valor rechazado:{}, argumentos:{}",
                        fErrI.getField(),
                        fErrI.getCode(),
                        fErrI.getDefaultMessage(),
                        fErrI.getRejectedValue(),
                        fErrI.getArguments() != null ? List.of(fErrI.getArguments()) : ""
                        )
                );
    }

    @GetMapping("/buscarArticulos")
    public String buscarArticulo(String cveArticulo, Model model){

        final String msgArtNoEspec = getMensajeI18n("servicio.articulos.error.artNoEspecificado");
        final String msgArtNoExiste = getMensajeI18n("servicio.articulos.error.artNoExiste");

        final String vistaCveArticuloAusente = checarCveArticuloAusente(cveArticulo, model, msgArtNoEspec);
        if (vistaCveArticuloAusente != null)
            return vistaCveArticuloAusente;

        Articulo art = this.gestorVtas.recuperarArticuloXID(cveArticulo);
        if(art == null){
            model.addAttribute("mensaje",msgArtNoExiste);
            return "consultaArticulo";
        }

        model.addAttribute("articulo",art);
        log.info(art.toString());
        return "consultaArticulo";
    }

    private String checarCveArticuloAusente(String cveArticulo, Model model, String msgArtNoEspec) {
        if(cveArticulo == null){
            model.addAttribute("mensaje", msgArtNoEspec);
            model.addAttribute("articulo", getArticuloVacio());
            return "consultaArticulo";
        }
        if(cveArticulo.trim().isEmpty()){
            model.addAttribute("mensaje", msgArtNoEspec);
            return "consultaArticulo";
        }
        return null;
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
