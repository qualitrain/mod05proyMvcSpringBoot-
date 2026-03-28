package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Articulo;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.DetalleVenta;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Venta;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.ArticuloDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.DetalleVentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.VentaDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorVentas implements  IGestorVentas {

    final private IGestorDatosSpring gestorDatos;

    public GestorVentas(IGestorDatosSpring gestorDatos) {
        this.gestorDatos = gestorDatos;
    }

    @Transactional(readOnly = true)
    public Venta recuperarVenta(int numVta){
        Venta vta = new Venta();
        VentaDTO   vtaDto = gestorDatos.leerVentaXID(numVta);
        //ToDo: Completar método
        return vta;
    }

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

    public Articulo getArticuloXID(String cveArt){
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
}
