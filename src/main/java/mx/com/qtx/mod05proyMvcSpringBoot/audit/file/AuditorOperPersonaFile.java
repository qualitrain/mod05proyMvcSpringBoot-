package mx.com.qtx.mod05proyMvcSpringBoot.audit.file;

import jakarta.annotation.PreDestroy;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.ILogPersona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Component
//@Primary
public class AuditorOperPersonaFile implements ILogPersona {
    private static Logger log = LoggerFactory.getLogger(AuditorOperPersonaFile.class);
    private final IPersistorLogPersona persistorLog;
    List<Operacion> operaciones =new ArrayList<>();

    public AuditorOperPersonaFile(IPersistorLogPersona persistorLog) {
        log.info("Se ha instanciado a " + this.getClass().getName());
        this.persistorLog = persistorLog;
    }

    @Override
    public int guardarOperacion(String tipoOperacion, Persona persona) {
        this.operaciones.add(new Operacion(persona.getIdPersona(),tipoOperacion));
        return this.operaciones.size();
    }

    @Override
    public void consultarOperacion(int folio) {
        if(folio > this.operaciones.size()){
            log.info("No hay operacion registrada con el folio " + folio);
        }
        Operacion operI = this.operaciones.get(folio - 1);
        log.info("Folio:{}, Tipo:{}, IdPersona:{}",folio, operI.tipo(), operI.id());
    }

    @Override
    public List<Integer> getFolios() {
        List<Integer> folios = new ArrayList<>();
        for(int i=1; i<=this.operaciones.size(); i++)
            folios.add(i);
        return folios;
    }

    @PreDestroy
    private void salvarOperaciones() {
        this.persistorLog.guardarOperaciones(log,operaciones);
    }

    public record Operacion(int id,String tipo) {
    }
}
