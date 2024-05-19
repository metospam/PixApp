package com.example.pixpalapp.validator;

import com.example.pixpalapp.payload.Request.User.UserCreateRequest;
import com.example.pixpalapp.repository.UserRepository;
import com.example.pixpalapp.validator.annotaion.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, UserCreateRequest> {

    UserRepository userRepository;

    @Override
    public boolean isValid(UserCreateRequest userRequest, ConstraintValidatorContext context) {
        boolean valid = userRepository.findByEmail(userRequest.getEmail()).isEmpty();

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
