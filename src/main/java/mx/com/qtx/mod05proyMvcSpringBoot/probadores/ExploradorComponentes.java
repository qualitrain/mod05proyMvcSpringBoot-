package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import mx.com.qtx.mod05proyMvcSpringBoot.audit.file.IPersistorLogPersona;
import mx.com.qtx.mod05proyMvcSpringBoot.servicios.ILogPersona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExploradorComponentes implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ExploradorComponentes.class);

    @Value("${qtx.com.saludo:No hay saludo}")
    private String saludo;
    @Autowired
    ApplicationContext ctxSpring;

    @Autowired
    List<Object> objetos;

    /* Está prohibida la auto-referencia
    @Autowired
    ExploradorComponentes yoMismo;
     */

    @Autowired
    List<IPersistorLogPersona> persistoresLog;

    @Autowired(required = false)
    List<ILogPersona> logsPersona;

    @Override
    public void run(String... args) throws Exception {
 //       explorarApplicationContext();

        log.info("{}", saludo);

        log.info("Hay {} objetos inyectados ",objetos.size());
        objetos.stream()
                .map(objI->objI.getClass().getName())
                .sorted()
                .forEach( nomObjI->{
                    log.info("   Se inyectó un objeto {}", nomObjI);
                }
        );

        log.info("Los beans asociados son: ");
        objetos.stream().map(objI-> getBeans(objI))
                        .sorted()
                       .forEach(lbeans->log.info("{}",lbeans));

        /* PROHIBIDO: auto-referencia, auto-inyeccion
        if(this.yoMismo == null){
            log.info("NO, no se auto-inyectó");
        }
        else{
            log.info("Zas! se auto-inyectó {} Vs {} ", this.hashCode(), this.yoMismo.hashCode());
        }
         */

        log.info("Se han inyectado {} implementaciones de IPersistorLogPersona", this.persistoresLog.size());
        log.info("Se han inyectado {} implementaciones de ILogPersona", this.logsPersona.size());
    }

    private String getBeans(Object objI) {
        return Arrays.stream(this.ctxSpring.getBeanNamesForType(objI.getClass()))
                .sorted().collect(Collectors.joining()) + " -> " + objI.getClass().getSimpleName();
    }

    private void explorarApplicationContext() {
        int nDefiniciones = this.ctxSpring.getBeanDefinitionCount();
        log.info("Hay {} beans en el contexto de Spring", nDefiniciones );
        Arrays.stream(this.ctxSpring.getBeanDefinitionNames())
                                     .sorted()
                                     .forEach(nomBeanI->{
                                         log.info("Bean registrado: {}", nomBeanI);
                                         Object objI = ctxSpring.getBean(nomBeanI);
                                         String nomClaseI = this.getNombreClase(objI);
                                         log.info("   su clase es: {}", nomClaseI);
                                     });

        try {
            Object obj = this.ctxSpring.getBean(ApplicationContext.class);
            log.info("La clase que implementa el ApplicationContext es {}", obj.getClass().getName());
        }
        catch(BeansException bex){
            log.info("ApplicationContext.class no está publicada como bean");
            log.info("No obstante, la clase que implementa la interface es {}",ctxSpring.getClass().getName());
        }
        String[] nomBeansDS = this.ctxSpring.getBeanNamesForType(DataSource.class);

        log.info("Los beans que implementan DataSorce son:");
        Arrays.stream(nomBeansDS).sorted().forEach(nbI->log.info("   {}",nbI));
    }

    private String getNombreClase(Object objI) {
        try{
            return objI.getClass().getName();
        }
        catch(Exception ex){
            return "Desconocido:" + ex.getClass().getName() + " [" + ex.getMessage() + "]";
        }
    }
}
