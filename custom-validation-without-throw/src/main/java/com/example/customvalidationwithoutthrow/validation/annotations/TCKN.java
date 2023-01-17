package com.example.customvalidationwithoutthrow.validation.annotations;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Constraint(validatedBy = {
        TCKN.TcknStringValidator.class
})
@Documented
public @interface TCKN {
    String message() default "TCKN düzgün formatta değil.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class TcknStringValidator implements ConstraintValidator<TCKN, String> {

        TCKN tcknAnnotation;

        @Override
        public void initialize(TCKN constraintAnnotation) {
            this.tcknAnnotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            int lastDigit = value.charAt(value.length()-1);
            return (value.length() == 11 && lastDigit % 2 == 0);
        }
    }
}
