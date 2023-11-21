package com.example.pixpalapp.validator;

import com.example.pixpalapp.model.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConfirmValidator implements ConstraintValidator<PasswordConfirm, UserDto> {

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        boolean valid = userDto.getPassword().equals(userDto.getConfirmPassword());

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
