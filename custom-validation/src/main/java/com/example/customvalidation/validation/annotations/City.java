package com.example.customvalidation.validation.annotations;

import com.example.customvalidation.model.enums.Cities;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Arrays;

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
@Constraint(validatedBy = {
        City.CityStringValidator.class
})
@Documented
public @interface City {
    String message() default "Girilen şehir kabul edilemez.";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CityStringValidator implements ConstraintValidator<City, String> {
        City constraintAnnotation;

        @Override
        public void initialize(City constraintAnnotation) {
            this.constraintAnnotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            return Arrays.stream(Cities.values()).map(Enum::toString).toList().contains(value);
        }
    }

    class CityEnumValidator implements ConstraintValidator<City, Cities> {
        City constraintAnnotation;

        @Override
        public void initialize(City constraintAnnotation) {
            this.constraintAnnotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(Cities value, ConstraintValidatorContext context) {
            return Arrays.stream(Cities.values()).toList().contains(value);
        }
    }
}