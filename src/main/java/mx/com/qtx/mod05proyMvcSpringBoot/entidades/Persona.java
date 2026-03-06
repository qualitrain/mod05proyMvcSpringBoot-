package mx.com.qtx.mod05proyMvcSpringBoot.entidades;

import java.time.LocalDate;
import java.util.Objects;

public class Persona {
    private int idPersona;
    private String nombre;
    private String direccion;
    private LocalDate fechaNacimiento;

    // Constructor vacío
    public Persona() {
    }

    // Constructor con todos los campos excepto ID (para inserción)
    public Persona(String nombre, String direccion, LocalDate fechaNacimiento) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Constructor completo
    public Persona(int idPersona, String nombre, String direccion, LocalDate fechaNacimiento) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y Setters
    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    // Métodos equals y hashCode para comparar objetos
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return idPersona == persona.idPersona;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPersona);
    }

    // Método toString para representación en String
    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}