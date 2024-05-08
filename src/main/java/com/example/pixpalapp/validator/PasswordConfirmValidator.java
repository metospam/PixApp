package com.example.pixpalapp.validator;

import com.example.pixpalapp.payload.Request.UserCreateRequest;
import com.example.pixpalapp.validator.annotaion.PasswordConfirm;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, UserCreateRequest> {

    @Override
    public boolean isValid(UserCreateRequest userRequest, ConstraintValidatorContext context) {
        boolean valid = userRequest.getPassword().equals(userRequest.getPasswordConfirmation());

        if(!valid){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Passwords must matches.")
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return valid;
    }
}
