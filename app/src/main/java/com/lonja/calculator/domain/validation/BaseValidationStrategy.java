package com.lonja.calculator.domain.validation;

import android.support.annotation.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class BaseValidationStrategy implements Validator.ValidatorStrategy {

    private Matcher mMatcher;

    private Pattern mPattern;

    protected boolean isFieldValid(String value, @NonNull String regexp) {
        mPattern = Pattern.compile(regexp);
        mMatcher = mPattern.matcher(value);
        return mMatcher.matches();
    }

    protected boolean isFieldValid(String value, @NonNull Pattern pattern) {
        mMatcher = pattern.matcher(value);
        return mMatcher.matches();
    }
}
