package com.lonja.calculator.domain.validation;

class PhoneValidator extends BaseValidationStrategy {

    private final String PHONE_PATTERN = "^[+]?[\\d\\s]{15,16}$";

    @Override
    public boolean isValid(String phone) {
        return isFieldValid(phone, PHONE_PATTERN);
    }
}
