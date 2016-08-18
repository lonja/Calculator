package com.lonja.calculator.ui.registration;


import android.content.Context;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;

import com.lonja.calculator.R;
import com.lonja.calculator.common.authentication.AuthenticationManager;
import com.lonja.calculator.data.entity.Account;
import com.lonja.calculator.domain.validation.FieldType;
import com.lonja.calculator.domain.validation.Validator;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;
import com.lonja.calculator.ui.registration.RegistrationContract.View;
import com.lonja.calculator.ui.registration.RegistrationContract.ViewModel;

import rx.Subscriber;
import rx.Subscription;

public class RegistrationViewModel extends BaseViewModel<View> implements ViewModel<View> {

    public final ObservableField<Account> account = new ObservableField<>(new Account());

    public final PhoneNumberFormattingTextWatcher textWatcher = new PhoneNumberFormattingTextWatcher();

    private Validator mValidator;

    private Context mContext;
    private AuthenticationManager mManager;

    private boolean isUsernameValid;
    private boolean isPasswordValid;
    private boolean isFirstNameValid;
    private boolean isLastNameValid;
    private boolean isPhoneValid;
    private boolean isEmailValid;

    public RegistrationViewModel(@NonNull Navigator navigator,
                                 @NonNull Context context,
                                 @NonNull AuthenticationManager authenticationManager,
                                 @NonNull Validator validator) {
        super(navigator);
        mContext = context;
        mManager = authenticationManager;
        mValidator = validator;
    }

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {

    }

    @Override
    public void register() {
        if (!isUsernameValid) {
            getView().showValidationError(R.string.invalid_username);
        } else if (!isPasswordValid) {
            getView().showValidationError(R.string.invalid_password);
        } else if (!isFirstNameValid) {
            getView().showValidationError(R.string.invalid_first_name);
        } else if (!isLastNameValid) {
            getView().showValidationError(R.string.invalid_last_name);
        } else if (!isEmailValid) {
            getView().showValidationError(R.string.invalid_email);
        } else if (!isPhoneValid) {
            getView().showValidationError(R.string.invalid_phone_number);
        } else {
            Subscription subscription = mManager.register(account.get())
                    .subscribe(new Subscriber<Account>() {
                        @Override
                        public void onCompleted() {
                            getView().showRegistrationCompleted();
                        }

                        @Override
                        public void onError(Throwable e) {
                            getView().showRegistrationError(e.getMessage());
                        }

                        @Override
                        public void onNext(Account account) {

                        }
                    });
            subscriptions.add(subscription);
        }
    }

    public void validateUsername(Editable value) {
        String username = value.toString();
        if (mValidator.isValid(username, FieldType.Username)) {
            isUsernameValid = true;
            return;
        }
        isUsernameValid = false;
    }

    public void validatePassword(Editable value) {
        String pass = value.toString();
        if (mValidator.isValid(pass, FieldType.Password)) {
            isPasswordValid = true;
            return;
        }
        isPasswordValid = false;
    }

    public void validateEmail(Editable value) {
        String email = value.toString();
        if (mValidator.isValid(email, FieldType.Email)) {
            isEmailValid = true;
            return;
        }
        isEmailValid = false;
    }

    public void validateFirstName(Editable value) {
        String name = value.toString();
        if (mValidator.isValid(name, FieldType.Name)) {
            isFirstNameValid = true;
            return;
        }
        isFirstNameValid = false;
    }

    public void validateLastName(Editable value) {
        String name = value.toString();
        if (mValidator.isValid(name, FieldType.Name)) {
            isLastNameValid = true;
            return;
        }
        isLastNameValid = false;
    }

    public void validatePhoneNumber(Editable value) {
        String phone = value.toString();
        if (mValidator.isValid(phone, FieldType.Phone)) {
            isPhoneValid = true;
            return;
        }
        isPhoneValid = false;
    }
}
