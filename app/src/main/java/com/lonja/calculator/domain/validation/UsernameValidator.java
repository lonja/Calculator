package com.lonja.calculator.domain.validation;

class UsernameValidator extends BaseValidationStrategy {

    private final String USERNAME_PATTERN = "^[A-Za-z\\d]+$";

    @Override
    public boolean isValid(String username) {
        return isFieldValid(username, USERNAME_PATTERN);
    }
}
