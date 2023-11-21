package com.example.pixpalapp.validator;

import com.example.pixpalapp.model.dto.UserDto;
import com.example.pixpalapp.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, UserDto> {

    UserRepository userRepository;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        boolean valid = userRepository.findByUsername(userDto.getUsername()).isEmpty();

        if(!valid){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Username  already exists.")
                    .addPropertyNode("username")
                    .addConstraintViolation();
        }

        return valid;
    }
}
