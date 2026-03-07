package mx.com.qtx.mod05proyMvcSpringBoot.audit.file;

import org.slf4j.Logger;

import java.util.List;

public interface IPersistorLogPersona {
    void guardarOperaciones(Logger log, List<AuditorOperPersonaFile.Operacion> operaciones);
}
