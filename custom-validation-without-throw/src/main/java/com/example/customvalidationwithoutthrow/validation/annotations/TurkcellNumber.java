package com.example.customvalidationwithoutthrow.validation.annotations;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Constraint(
        validatedBy = {
                TurkcellNumber.TurkcellNumberStringValidator.class
        }
)
@Documented
public @interface TurkcellNumber {
    String message() default "Bu telefon numarası turkcell'e ait değil";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class TurkcellNumberStringValidator implements ConstraintValidator<TurkcellNumber, String>{
        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return value.startsWith("0533");
        }

        @Override
        public void initialize(TurkcellNumber constraintAnnotation) {
        }
    }
}
