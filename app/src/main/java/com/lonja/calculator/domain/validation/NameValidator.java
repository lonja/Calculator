package com.lonja.calculator.domain.validation;

class NameValidator extends BaseValidationStrategy {

    private final String NAME_PATTERN = "^[\\p{L}]+$";

    @Override
    public boolean isValid(String name) {
        return isFieldValid(name, NAME_PATTERN);
    }
}
