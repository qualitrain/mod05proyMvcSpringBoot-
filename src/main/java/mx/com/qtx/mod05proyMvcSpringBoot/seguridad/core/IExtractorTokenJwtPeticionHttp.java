package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core;

import jakarta.servlet.http.HttpServletRequest;

public interface IExtractorTokenJwtPeticionHttp {
    boolean peticionTieneTokenJWTValido(HttpServletRequest request);

    String getNombreUsuarioSiTokenEsValido(HttpServletRequest request);
}