package mx.com.qtx.mod05proyMvcSpringBoot.audit;

import mx.com.qtx.mod05proyMvcSpringBoot.audit.file.AuditorOperPersonaFile;
import org.slf4j.Logger;

import java.util.List;

public interface IPersistorLogPersona {
    void guardarOperaciones(Logger log, List<AuditorOperPersonaFile.Operacion> operaciones);
}
