package mx.com.qtx.mod05proyMvcSpringBoot.probadores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ExploradorComponentes implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(ExploradorComponentes.class);

    @Autowired
    ApplicationContext ctxSpring;

    @Override
    public void run(String... args) throws Exception {
        int nDefiniciones = this.ctxSpring.getBeanDefinitionCount();
        log.info("Hay {} beans en el contexto de Spring", nDefiniciones );
        Arrays.stream(this.ctxSpring.getBeanDefinitionNames())
                                     .sorted()
                                    .forEach(beanI->log.info("Bean registrado: {}", beanI));
    }
}
