package com.example.pixpalapp.manager.Impl;

import com.example.pixpalapp.manager.ErrorManager;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class ErrorManagerImpl implements ErrorManager {

    @Override
    public String displayError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }

}
