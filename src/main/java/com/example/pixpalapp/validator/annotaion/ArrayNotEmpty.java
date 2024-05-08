package com.example.pixpalapp.validator.annotaion;


import com.example.pixpalapp.validator.ArrayNotEmptyValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ArrayNotEmptyValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArrayNotEmpty {
    String message() default "Array empty";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
