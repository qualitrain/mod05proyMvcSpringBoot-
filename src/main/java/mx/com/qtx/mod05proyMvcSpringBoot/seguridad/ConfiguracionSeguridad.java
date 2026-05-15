package mx.com.qtx.mod05proyMvcSpringBoot.seguridad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfiguracionSeguridad {

    private static final Logger log = LoggerFactory.getLogger(ConfiguracionSeguridad.class);

    @Bean
    SecurityFilterChain configurarCadenaFiltradoSeguridad(HttpSecurity http){
        http.authorizeHttpRequests( (aut)->aut.anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    UserDetailsService crearBDUsuarios(){
        UserDetails usuario1 = User.withDefaultPasswordEncoder().username("alex")
                                                                .password("tekamachalko")
                                                                .roles("admin","vtas")
                                                                .build();
        UserDetails usuario2 = User.withDefaultPasswordEncoder().username("david")
                .password("tekolutla")
                .roles("vtas","compras")
                .build();
        UserDetails usuario3 = User.withDefaultPasswordEncoder().username("tavo")
                .password("tlatelolko")
                .roles("compras")
                .build();

        InMemoryUserDetailsManager bdUSuarios = new InMemoryUserDetailsManager(usuario1, usuario2, usuario3);
        return bdUSuarios;
    }
}
