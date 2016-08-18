package com.lonja.calculator.domain.validation;

public class PairsValidator extends BaseValidationStrategy {

    private final String PAIRS_PATTERN = "^(\\d+\\s{0,1})+$";

    @Override
    public boolean isValid(String pairs) {
        return isFieldValid(pairs, PAIRS_PATTERN);
    }
}