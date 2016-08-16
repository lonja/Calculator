package com.lonja.calculator.domain.validation;

import android.util.Patterns;

class EmailValidator extends BaseValidationStrategy {

    @Override
    public boolean isValid(String email) {
        return isFieldValid(email, Patterns.EMAIL_ADDRESS);
    }
}
