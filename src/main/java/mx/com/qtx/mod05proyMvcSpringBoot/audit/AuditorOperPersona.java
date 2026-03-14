package mx.com.qtx.mod05proyMvcSpringBoot.audit;

import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.ILogPersona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class AuditorOperPersona implements ILogPersona {
    private static Logger log = LoggerFactory.getLogger(AuditorOperPersona.class);
    private Map<Integer,Operacion>  mapOperaciones = new TreeMap<>();

    @Override
    public int guardarOperacion(String tipoOperacion, Persona persona) {
        int nFolio = this.mapOperaciones.size() + 1;
        Operacion operI = new Operacion(nFolio,tipoOperacion,persona);
        this.mapOperaciones.put(nFolio, operI);
        return nFolio;
    }

    @Override
    public void consultarOperacion(int folio) {
        Operacion oper = mapOperaciones.get(folio);
        if(oper != null)
            log.info(oper.toString());
        else
            log.info("No existe operacion con el folio " + folio);
    }

    @Override
    public List<Integer> getFolios() {
        return new ArrayList<>( this.mapOperaciones.keySet() );
    }

    private record Operacion(int folio, String tipo, Persona p){};
}
