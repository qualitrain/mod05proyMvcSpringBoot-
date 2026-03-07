package mx.com.qtx.mod05proyMvcSpringBoot.audit.file;

import jakarta.annotation.PostConstruct;
import mx.com.qtx.mod05proyMvcSpringBoot.audit.AuditorOperPersona;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.ILogPersona;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class ConfiguracionLogPersona {
    private static Logger log = LoggerFactory.getLogger(ConfiguracionLogPersona.class);
    private static final String PROP_FORMATO_LOG_PERSONA = "qtx.com.logpersona.formato";
    private static final String PROP_TIPO_LOG_PERSONA = "qtx.com.logpersona.tipo";

    private final Environment env;

    public ConfiguracionLogPersona(Environment env) {
        this.env = env;
    }

    @PostConstruct
    public void checarConfiguracion(){
        String propBuscada = PROP_FORMATO_LOG_PERSONA;
        String valorFormatoLogpersona = this.env.getProperty(propBuscada);
        log.info("{} = {}", propBuscada,valorFormatoLogpersona);
        propBuscada = PROP_TIPO_LOG_PERSONA;
        valorFormatoLogpersona = this.env.getProperty(propBuscada);
        log.info("{} = {}", propBuscada,valorFormatoLogpersona);    }

    @Bean
    public IPersistorLogPersona getIPersistorLogPersona(){
        log.info("creando bean IPersistorLogPersona");

        String valorFormatoLogpersona = this.env.getProperty(PROP_FORMATO_LOG_PERSONA);
        if(valorFormatoLogpersona == null){
            return new IPersistorLogPersona() {
                @Override
                public void guardarOperaciones(Logger log, List<AuditorOperPersonaFile.Operacion> operaciones) {}
            };
        }

        switch(valorFormatoLogpersona){
            case "json" -> { return new PersistorLogPersonaFileJson(); }
            case "txt" -> { return new PersistorLogPersonaFile(); }
            default -> {
                return null;
            }
        }
    }

    @Bean
    public ILogPersona getILogPersona(IPersistorLogPersona persistorLogPersona){
        log.info("creando bean ILogPersona");
        String tipoLogpersona = this.env.getProperty(PROP_TIPO_LOG_PERSONA);
        switch(tipoLogpersona){
            case "consola"->{
                return new AuditorOperPersona();
            }
            case "archivo"->{
                return new AuditorOperPersonaFile(persistorLogPersona);
            }
        }
        return null;
    }
}
