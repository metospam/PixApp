package com.example.pixpalapp.manager;

import org.springframework.validation.FieldError;

public interface ErrorManager {
    String displayError(FieldError error);
}
