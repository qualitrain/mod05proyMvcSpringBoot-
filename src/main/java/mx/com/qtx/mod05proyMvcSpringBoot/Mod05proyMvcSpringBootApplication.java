package mx.com.qtx.mod05proyMvcSpringBoot;

import mx.com.qtx.mod05proyMvcSpringBoot.web.ErrorCte;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import java.util.Locale;

@SpringBootApplication
public class Mod05proyMvcSpringBootApplication {

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

}
