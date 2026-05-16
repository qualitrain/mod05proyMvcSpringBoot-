package mx.com.qtx.mod05proyMvcSpringBoot.seguridad;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;

import javax.sql.DataSource;
import java.time.LocalTime;
import java.util.Objects;

@Configuration
public class ConfiguracionSeguridad {

    private static final Logger log = LoggerFactory.getLogger(ConfiguracionSeguridad.class);

    public static final int HORA_INICIO_DIA_LABORABLE = 9;
    public static final int HORA_FIN_DIA_LABORABLE = 18;
    public static final String PREFIJO_IP = "192.168";


    @Bean
    @ConfigurationProperties("spring.datasource.security")
    public DataSourceProperties seguridadProperties() {
        return new DataSourceProperties();
    }

    @Bean
      public DataSource seguridadDataSource(@Qualifier("seguridadProperties")DataSourceProperties seguridadProperties) {
        log.info("Creando DataSource para seguridad");
        return seguridadProperties.initializeDataSourceBuilder().build();
    }

    //@Bean
    SecurityFilterChain configurarCadenaFiltradoSeguridadMuyBasica(HttpSecurity http){
        http.authorizeHttpRequests( (aut)->aut.anyRequest().authenticated())
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults());

        return http.build();
    }

    // @Bean
    SecurityFilterChain configurarCadenaFiltradoSeguridad(HttpSecurity http){
        http.authorizeHttpRequests( (aut)->aut
                        .requestMatchers("/*.css","/*.png","/index.html","/error*","/","/error/**").permitAll()
                        .requestMatchers("/login","/logout").permitAll()
                        .requestMatchers("/consultarArticulo","/buscarArticulos").hasRole("vtas")
                        .requestMatchers("/insertarArticulo","/procesarInsercionArticulo").hasRole("compras")
                        .requestMatchers("/api/**").hasRole("cte")
                        .requestMatchers("/**").authenticated()
                )
                .csrf(c->c.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    //@Bean
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
                .roles("compras","cte")
                .build();

        InMemoryUserDetailsManager bdUSuarios = new InMemoryUserDetailsManager(usuario1, usuario2, usuario3);
        return bdUSuarios;
    }

    //@Bean
    UserDetailsManager getBdUsuarios(@Qualifier("seguridadDataSource") DataSource seguridadDataSource){
        UserDetails usuarioAlex = User.withDefaultPasswordEncoder().username("alex")
                .password("tekamachalko")
                .roles("admin","vtas")
                .build();
        UserDetails usuarioDavid = User.withDefaultPasswordEncoder().username("david")
                .password("tekolutla")
                .roles("vtas","compras")
                .build();
        UserDetails usuarioTavo = User.withDefaultPasswordEncoder().username("tavo")
                .password("tlatelolko")
                .roles("compras","cte")
                .build();

        JdbcUserDetailsManager managerUsuarios = new JdbcUserDetailsManager(seguridadDataSource);

        if(managerUsuarios.userExists(usuarioAlex.getUsername()) == false){
            managerUsuarios.createUser(usuarioAlex);
        }
        if(managerUsuarios.userExists(usuarioDavid.getUsername()) == false){
            managerUsuarios.createUser(usuarioDavid);
        }
        if(managerUsuarios.userExists(usuarioTavo.getUsername()) == false){
            managerUsuarios.createUser(usuarioTavo);
        }
        return managerUsuarios;
    }

    @Bean
    UserDetailsManager getBdUsuarios_pwdConHash(@Qualifier("seguridadDataSource") DataSource seguridadDataSource){
        UserDetails usuarioAlex = User.withUsername("alex")
                                        .password("{bcrypt}$2a$10$MJQOHZ61IRh4UWz6T0xB2eXWBoaKJ4VvK18MdwVjl8lSMaL/jOK32")
                                        .roles("admin","vtas")
                                        .build();

        UserDetails usuarioDavid = User.withUsername("david")
                .password("{bcrypt}$2a$10$pMX/cg/gYMsOYV6SGa0byOZx93I2RsL6BMlKxHIvMiUnLnIfQL6v6")
                .roles("vtas","compras")
                .build();

        UserDetails usuarioTavo = User.withUsername("tavo")
                .password("{bcrypt}$2a$10$Pb4kIdnbSsyVFzalrl9TMuQlopJSxm99odTlNAk7l0O8Y2hiPFXuC")
                .roles("compras","cte")
                .build();

        JdbcUserDetailsManager managerUsuarios = new JdbcUserDetailsManager(seguridadDataSource);

        if(managerUsuarios.userExists(usuarioAlex.getUsername()) == false){
            managerUsuarios.createUser(usuarioAlex);
        }
        if(managerUsuarios.userExists(usuarioDavid.getUsername()) == false){
            managerUsuarios.createUser(usuarioDavid);
        }
        if(managerUsuarios.userExists(usuarioTavo.getUsername()) == false){
            managerUsuarios.createUser(usuarioTavo);
        }
        return managerUsuarios;
    }

    @Bean
    SecurityFilterChain configurarCadenaFiltradoSeguridad_autorizadorPersonalizado(HttpSecurity http,
                        AuthorizationManager<RequestAuthorizationContext> autorizadorAdminHorarioLaboralIpInterna){

        http.authorizeHttpRequests( (aut)->aut
                        .requestMatchers("/*.css","/*.png","/index.html","/error*","/","/error/**").permitAll()
                        .requestMatchers("/login","/logout").permitAll()
                        .requestMatchers("/consultarArticulo","/buscarArticulos").hasRole("vtas")

                        .requestMatchers("/insertarArticulo","/procesarInsercionArticulo")
                                .access(autorizadorAdminHorarioLaboralIpInterna)

                        .requestMatchers("/api/**").hasRole("cte")
                        .requestMatchers("/**").authenticated()
                )
                .csrf(c->c.disable())
                .httpBasic(Customizer.withDefaults())
                .formLogin(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> autorizadorRolAdmin() {
        AuthorizationManager<RequestAuthorizationContext> autorizador;

        autorizador = (auth, ctx) ->{
            log.info("autorizadorRolAdmin.verify()");
            Authentication tknAutenticacion = auth.get();
            if(tknAutenticacion == null)
                return new AuthorizationDecision(false);

            log.info("autorizadorRolAdmin.verify Authorities={}",tknAutenticacion.getAuthorities());
            return new AuthorizationDecision(
                    tknAutenticacion.getAuthorities()
                                    .stream()
                                    .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_admin"))
            );
        };

        return autorizador;
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> autorizadorHorarioLaboral() {
        return (auth, ctx) -> {
            log.info("autorizadorHorarioLaboral.verify()");
            int horaActual = LocalTime.now().getHour();
            log.info("autorizadorHorarioLaboral.verify horaActual={}",horaActual);
            return new AuthorizationDecision(esHoraLaborable(horaActual));
        };
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> autorizadorIpInterna() {
        return (auth, ctx) -> {
            log.info("autorizadorIpInterna.verify()");
            String ip = ctx.getRequest().getRemoteAddr();
            log.info("autorizadorIpInterna.verify ip={}",ip);
            return new AuthorizationDecision(ip.startsWith(PREFIJO_IP));
        };
    }

    @Bean
    public AuthorizationManager<RequestAuthorizationContext> autorizadorAdminHorarioLaboralIpInterna(
            AuthorizationManager<RequestAuthorizationContext> autorizadorRolAdmin,
            AuthorizationManager<RequestAuthorizationContext> autorizadorHorarioLaboral,
            AuthorizationManager<RequestAuthorizationContext> autorizadorIpInterna) {

        return (auth, ctx) -> {
            log.info("autorizadorAdminHorarioLaboralIpInterna.verify()");
            if(autorizadorRolAdmin.authorize(auth, ctx).isGranted())
                log.info("autorizadorRolAdmin autoriza acceso");
            else
                log.warn("autorizadorRolAdmin deniega acceso");
            if(autorizadorHorarioLaboral.authorize(auth, ctx).isGranted())
                log.info("autorizadorHorarioLaboral autoriza acceso");
            else
                log.warn("autorizadorHorarioLaboral deniega acceso");
            if(autorizadorIpInterna.authorize(auth, ctx).isGranted())
                log.info("autorizadorIpInterna autoriza acceso");
            else
                log.warn("autorizadorIpInterna deniega acceso");

            boolean granted =
                    autorizadorRolAdmin.authorize(auth, ctx).isGranted() &&
                    autorizadorHorarioLaboral.authorize(auth, ctx).isGranted() &&
                    autorizadorIpInterna.authorize(auth, ctx).isGranted();

            return new AuthorizationDecision(granted);
        };
    }

    private static boolean esHoraLaborable(int horaActual) {
        return horaActual >= HORA_INICIO_DIA_LABORABLE && horaActual <= HORA_FIN_DIA_LABORABLE;
    }


}
