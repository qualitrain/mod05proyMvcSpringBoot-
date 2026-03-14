package mx.com.qtx.mod05proyMvcSpringBoot.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AuditUtil {
     private static Logger log = LoggerFactory.getLogger(AuditUtil.class);

     public static void explorarEnvironment(Environment env){
        log.info("Implementacion de Environment:{}", env.getClass().getName());
        String activeProfiles = Arrays.stream(env.getActiveProfiles()).collect(Collectors.joining(", "));
        log.info("activeProfiles:{}",activeProfiles);
        String defaultProfiles = Arrays.stream(env.getDefaultProfiles()).collect(Collectors.joining(", "));
        log.info("defaultProfiles:{}",defaultProfiles);

         ConfigurableEnvironment cEnv = (ConfigurableEnvironment) env;
         log.info("Properties Sources:");
         MutablePropertySources ps = cEnv.getPropertySources();
         List<PropertySource<?>> lstPs = ps.stream().toList();
         for(PropertySource<?> psI:lstPs){
             log.info("   {}: {}, source:{}",psI.getName(), psI.getClass().getName(), psI.getSource().getClass().getName());
             if (psI instanceof EnumerablePropertySource<?> eps) {
                 for (String name : eps.getPropertyNames()) {
                     Object value = eps.getProperty(name);
                     log.info("      {}={}",name, value);
                 }

             }
         }
     }
}
