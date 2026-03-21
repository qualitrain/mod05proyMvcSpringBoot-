package mx.com.qtx.mod05proyMvcSpringBoot.servicios;

import mx.com.qtx.mod05proyMvcSpringBoot.objetosNegocio.Venta;
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
}
