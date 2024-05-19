package com.example.pixpalapp.validator;

import com.example.pixpalapp.payload.Request.User.UserCreateRequest;
import com.example.pixpalapp.repository.UserRepository;
import com.example.pixpalapp.validator.annotaion.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, UserCreateRequest> {

    UserRepository userRepository;

    @Override
    public boolean isValid(UserCreateRequest userRequest, ConstraintValidatorContext context) {
        boolean valid = userRepository.findByUsername(userRequest.getUsername()).isEmpty();

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
