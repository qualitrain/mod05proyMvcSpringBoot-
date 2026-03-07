package mx.com.qtx.mod05proyMvcSpringBoot.audit;

import jakarta.annotation.PreDestroy;
import mx.com.qtx.mod05proyMvcSpringBoot.entidades.Persona;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.ILogPersona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Primary
public class AuditorOperPersonaFile implements ILogPersona {
    private static Logger log = LoggerFactory.getLogger(AuditorOperPersonaFile.class);
    List<Operacion> operaciones =new ArrayList<>();

    public AuditorOperPersonaFile() {
        log.info("Se ha instanciado a " + this.getClass().getName());
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
        log.info("Folio:{}, Tipo:{}, IdPersona:{}",folio, operI.tipo, operI.id);
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
        log.info("Salvando log de operaciones...");
        final String nombreArchivo = generarNombreArchivo();

        try (PrintWriter writer = new PrintWriter(new FileWriter(nombreArchivo))) {
            for (int i = 0; i < operaciones.size(); i++) {
                Operacion op = operaciones.get(i);
                int folio = i + 1; // El folio es el índice + 1
                writer.printf("Folio:%5d, Id:%5d, Tipo:%s%n", folio, op.id, op.tipo);
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

    private record Operacion(int id,String tipo){};
}
