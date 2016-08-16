package com.lonja.calculator.domain.validation;

class PasswordValidator extends BaseValidationStrategy {

    private final String PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{4,8}$";

    @Override
    public boolean isValid(String password) {
        return isFieldValid(password, PASSWORD_PATTERN);
    }
}
