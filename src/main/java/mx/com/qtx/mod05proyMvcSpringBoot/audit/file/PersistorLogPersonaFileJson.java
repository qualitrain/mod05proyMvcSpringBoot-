package mx.com.qtx.mod05proyMvcSpringBoot.audit.file;

import org.slf4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Primary
@Component
public class PersistorLogPersonaFileJson extends PersistorLogPersonaFile {
    @Override
    public void guardarOperaciones(Logger log, List<AuditorOperPersonaFile.Operacion> operaciones) {
        log.info("Salvando log de operaciones...");
        final String nombreArchivo = this.generarNombreArchivo();

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            writer.printf("{ \"operaciones\":[%n");
            for (int i = 0; i < operaciones.size(); i++) {
                if(i>0)
                    writer.printf(",%n");
                AuditorOperPersonaFile.Operacion op = operaciones.get(i);
                int folio = i + 1; // El folio es el índice + 1
                writer.printf("   {%n    \"folio\":%d, \"id\":%d, \"tipo\":\"%s\"%n   }", folio, op.id(), op.tipo());
            }
            writer.printf("%n]}");
            log.info("Operaciones guardadas en archivo: " + nombreArchivo);
        }
        catch (IOException e) {
            log.error("Error al guardar operaciones: " + e.getMessage(), e);
        }

    }

    @Override
    protected String getExtensionArchivo() {
        return ".json";
    }
}
