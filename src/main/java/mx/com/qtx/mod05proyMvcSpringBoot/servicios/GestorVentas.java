package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.core.IGestorVentas;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Categoria;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.DetalleVenta;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Venta;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.ArticuloDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.DetalleVentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.VentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.err.InsercionDuplicadaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Service
public class GestorVentas implements IGestorVentas {
    private static final Logger log = LoggerFactory.getLogger(GestorVentas.class);

    final private IGestorDatosSpring gestorDatos;

    public GestorVentas(IGestorDatosSpring gestorDatos) {
        this.gestorDatos = gestorDatos;
    }

    @Override
    @Transactional(readOnly = true)
    public Venta recuperarVenta(int numVta){
        Venta vta = new Venta();
        VentaDTO   vtaDto = gestorDatos.leerVentaXID(numVta);
        //ToDo: Completar método
        return vta;
    }

    @Override
    @Transactional()
    public int registrarVenta(Venta vta){
        VentaDTO vtaXinsertar = new VentaDTO();
        vtaXinsertar.setFechaVenta(vta.getFecha());
        vtaXinsertar.setIdPersonaCte(vta.getCliente().getIdPersona());
        vtaXinsertar.setIdPersonaVendedor(vta.getVendedor().getIdPersona());
        VentaDTO vtaInsertada = gestorDatos.insertarVenta(vtaXinsertar);

        for(DetalleVenta detVtaI:vta.getDetallesVta()){
            DetalleVentaDTO detDto = new DetalleVentaDTO();
            detDto.setNumVenta(vtaInsertada.getNumVenta());
            detDto.setNumDetalle(detVtaI.getNumDetalle());
            detDto.setCantidad(detVtaI.getCatidad());
            detDto.setPrecioUnitario(detVtaI.getPrecio().floatValue());
            detDto.setCveArticulo(detVtaI.getArticulo().getCveArticulo());
            gestorDatos.insertarDetalleVenta(detDto);
        }

        return vtaInsertada.getNumVenta();
    }

    @Override
    public Articulo recuperarArticuloXID(String cveArt){
        ArticuloDTO artDto = gestorDatos.leerArticuloXID(cveArt);
        if(artDto == null)
            return null;
        Articulo art = new Articulo();
        art.setCveArticulo(artDto.getCveArticulo());
        art.setDescripcion(artDto.getDescripcion());
        art.setCostoProv1(artDto.getCostoProv1());
        art.setPrecioLista(artDto.getPrecioLista());
        return art;
    }

    @Override
    public List<Categoria> recuperarCategorias(){
        List<Categoria> lstCategorias = new ArrayList<>();
        this.gestorDatos.leerCategorias().forEach(catI->lstCategorias.add(new Categoria(catI.getCveCategoria(),catI.getDescripcion())));
        return lstCategorias;
    }

    @Override
    public Map<String,String> recuperarMapCategoriasAlf(){
        Map<String,String> mapCategorias = new TreeMap<>();
        this.recuperarCategorias().forEach(catI->mapCategorias.put(catI.getDescripcion(),catI.getCveCategoria()));
        return mapCategorias;
    }

    @Override
    @Transactional
    public String insertarArticulo(Articulo art){

        if(art == null){
            IllegalArgumentException iaex = new IllegalArgumentException("articulo nulo");
            log.error(iaex.getMessage());
            throw iaex;
        }

        ArticuloDTO artPreexistente = this.gestorDatos.leerArticuloXID(art.getCveArticulo());
        if(artPreexistente != null){
            InsercionDuplicadaException idex = new InsercionDuplicadaException("Insercion articulo duplicado [" + art.getCveArticulo() + "]");
            idex.setDescripcion("Se ha intentado insertar un artículo que ya existe en la BD:" + art.toString());
            idex.setOperacionIntentada(this.getClass().getName() + "." + "insertarArticulo()");
            idex.setRegla("No pueden existir DOS artículos con la misma clave");
            idex.setRecomendacion("Revise el catálogo de artículos actual y compare contra la clave del artículo por insertar");
            log.error(idex.getDescripcion());
            throw idex;
        }

        ArticuloDTO artDto = new ArticuloDTO();
        artDto.setCveArticulo(art.getCveArticulo());
        artDto.setDescripcion(art.getDescripcion());
        artDto.setCostoProv1(art.getCostoProv1());
        artDto.setPrecioLista(art.getPrecioLista());
        artDto.setDescontinuado(art.isDescontinuado());
        artDto.setCveCategoria(art.getCategoria().getCveCategoria());
        artDto.setFecUltimaCompra(art.getFecUltimaCompra());

        this.gestorDatos.insertarArticulo(artDto);
        log.info("Se ha insertado {}",art);

        return artDto.getCveArticulo();
    }
}
