package mx.com.qtx.mod05proyMvcSpringBoot;

import mx.com.qtx.mod05proyMvcSpringBoot.web.ErrorCte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import javax.sql.DataSource;
import java.util.Locale;

    @SpringBootApplication
    public class Mod05proyMvcSpringBootApplication {

    private static Logger log = LoggerFactory.getLogger(Mod05proyMvcSpringBootApplication.class);

	public static void main(String[] args) {

        SpringApplication.run(Mod05proyMvcSpringBootApplication.class, args);
	}

    @Bean
    public LocaleResolver localeResolver() {
        FixedLocaleResolver resolver = new FixedLocaleResolver(Locale.of("es","MX"));
    //    FixedLocaleResolver resolver = new FixedLocaleResolver(Locale.US);
    //    FixedLocaleResolver resolver = new FixedLocaleResolver(Locale.of("or","IN"));
        return resolver;
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.negocio")
    public DataSourceProperties negocioProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource negocioDataSource(DataSourceProperties negocioProperties) {
        log.info("Creando DataSource para negocio");
        return negocioProperties.initializeDataSourceBuilder().build();
    }
}
