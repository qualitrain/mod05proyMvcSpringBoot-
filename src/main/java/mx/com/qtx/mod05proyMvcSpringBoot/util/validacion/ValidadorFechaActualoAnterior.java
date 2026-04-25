package mx.com.qtx.mod05proyMvcSpringBoot.util.validacion;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class ValidadorFechaActualoAnterior implements ConstraintValidator<FechaActualoAnterior, LocalDate> {
    @Override
    public void initialize(FechaActualoAnterior constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate fechaXvalidar, ConstraintValidatorContext context) {
        if(fechaXvalidar == null)
            return true;
        if(fechaXvalidar.isAfter(LocalDate.now()))
            return false;
        return true;
    }
}
