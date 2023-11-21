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
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserDto> {

    UserRepository userRepository;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext context) {
        boolean valid = userRepository.findByEmail(userDto.getEmail()).isEmpty();

        if(!valid){
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("Email already exists.")
                    .addPropertyNode("email")
                    .addConstraintViolation();
        }

        return valid;
    }
}
