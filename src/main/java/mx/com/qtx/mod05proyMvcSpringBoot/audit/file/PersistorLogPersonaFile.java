package mx.com.qtx.mod05proyMvcSpringBoot.audit.file;

import mx.com.qtx.mod05proyMvcSpringBoot.audit.IPersistorLogPersona;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class PersistorLogPersonaFile implements IPersistorLogPersona {

    public void guardarOperaciones(Logger log, List<AuditorOperPersonaFile.Operacion> operaciones) {
        log.info("Salvando log de operaciones...");
        final String nombreArchivo = generarNombreArchivo();

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (int i = 0; i < operaciones.size(); i++) {
                AuditorOperPersonaFile.Operacion op = operaciones.get(i);
                int folio = i + 1; // El folio es el índice + 1
                writer.printf("Folio:%5d, Id:%5d, Tipo:%s%n", folio, op.id(), op.tipo());
            }
            log.info("Operaciones guardadas en archivo: " + nombreArchivo);
        }
        catch (IOException e) {
            log.error("Error al guardar operaciones: " + e.getMessage(), e);
        }
    }

    private static String generarNombreArchivo() {
        LocalDateTime ahora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String timestamp = ahora.format(formatter);
        String nombreArchivo = "operaciones_" + timestamp + ".txt";
        return nombreArchivo;
    }
}
