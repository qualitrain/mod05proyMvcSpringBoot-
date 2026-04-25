package mx.com.qtx.mod05proyMvcSpringBoot.util.validacion;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ValidadorFechaActualoAnterior.class)
public @interface FechaActualoAnterior {
    String message() default "Debe indicarse una fecha que no sea futura";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
