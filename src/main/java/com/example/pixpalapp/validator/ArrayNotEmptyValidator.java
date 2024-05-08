package com.example.pixpalapp.validator;

import com.example.pixpalapp.validator.annotaion.ArrayNotEmpty;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

public class ArrayNotEmptyValidator implements ConstraintValidator<ArrayNotEmpty, List<?>> {

    @Override
    public boolean isValid(List list, ConstraintValidatorContext context) {
        if (list == null || list.isEmpty()) {
            addConstraintViolation(context, "List is empty");
            return false;
        }

        for (Object item : list) {
            if (item == null || (item instanceof String && ((String) item).isEmpty())) {
                addConstraintViolation(context, "List contains empty or null values");
                return false;
            }
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context
                .buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
