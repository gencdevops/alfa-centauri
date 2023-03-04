package com.example.cgorder.validation.annotation;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.time.LocalDate;

@Constraint(validatedBy = {
        ExpireYear.ExpireYearStringValidator.class,
        ExpireYear.ExpireYearNumberValidator.class
})
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExpireYear {
    String message() default "Invalid expire year";
    Class[] groups() default {};
    Class[] payload() default {};

    class ExpireYearStringValidator implements ConstraintValidator<ExpireYear, String> {
        ExpireYear annotation;

        @Override
        public void initialize(ExpireYear constraintAnnotation) {
            this.annotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            try {
                Integer expireYearAsInteger = Integer.valueOf(value);

                if(expireYearAsInteger < LocalDate.now().getYear())
                    return false;
            } catch (NumberFormatException e){
                return false;
            }

            return true;
        }
    }

    class ExpireYearNumberValidator implements ConstraintValidator<ExpireYear, Number> {
        ExpireYear annotation;

        @Override
        public void initialize(ExpireYear constraintAnnotation) {
            this.annotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(Number value, ConstraintValidatorContext context) {
            return value.doubleValue() >= LocalDate.now().getYear();
        }
    }

}