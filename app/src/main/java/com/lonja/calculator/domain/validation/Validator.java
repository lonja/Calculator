package com.lonja.calculator.domain.validation;

public class Validator {

    private ValidatorStrategy mCurrentValidator;

    public Validator() {

    }

    public boolean isValid(String value, FieldType field) {
        switch (field) {
            case Username:
                mCurrentValidator = new UsernameValidator();
                break;
            case Password:
                mCurrentValidator = new PasswordValidator();
                break;
            case Email:
                mCurrentValidator = new EmailValidator();
                break;
            case Name:
                mCurrentValidator = new NameValidator();
                break;
            case Phone:
                mCurrentValidator = new PhoneValidator();
                break;
            case Pairs:
                mCurrentValidator = new PairsValidator();
        }
        return mCurrentValidator.isValid(value);
    }

    interface ValidatorStrategy {

        boolean isValid(String value);
    }
}
