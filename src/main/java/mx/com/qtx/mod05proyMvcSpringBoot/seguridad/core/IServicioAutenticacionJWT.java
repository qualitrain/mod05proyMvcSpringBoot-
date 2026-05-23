package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core;


public interface IServicioAutenticacionJWT {
    IResultadoOperacion registrarAutenticacion(Autenticacion aut);
}