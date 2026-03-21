package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.DetalleVenta;
import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Venta;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.DetalleVentaDTO;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.dtos.VentaDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GestorVentas {

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
}
