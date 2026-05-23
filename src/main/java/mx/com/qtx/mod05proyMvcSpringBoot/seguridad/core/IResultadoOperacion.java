package mx.com.qtx.mod05proyMvcSpringBoot.seguridad.core;

public interface IResultadoOperacion {
    Object getObjResultadoOk();

    void setObjResultadoOk(Object objResultadoOk);

    boolean todoOk();

    void agregarError(int codError, String adicion);

    String getResumenErrores();
}