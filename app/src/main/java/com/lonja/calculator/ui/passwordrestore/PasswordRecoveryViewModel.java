package com.lonja.calculator.ui.passwordrestore;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;

import com.lonja.calculator.R;
import com.lonja.calculator.data.repository.AccountsRepository;
import com.lonja.calculator.domain.validation.FieldType;
import com.lonja.calculator.domain.validation.Validator;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;
import com.lonja.calculator.ui.passwordrestore.PasswordRecoveryContract.View;
import com.lonja.calculator.ui.passwordrestore.PasswordRecoveryContract.ViewModel;

import hugo.weaving.DebugLog;
import rx.Subscriber;
import rx.Subscription;

public class PasswordRecoveryViewModel extends BaseViewModel<View> implements ViewModel<View> {

    public final ObservableField<String> email = new ObservableField<>();

    public final ObservableField<String> username = new ObservableField<>();

    private AccountsRepository mRepository;

    private Validator mValidator;

    private boolean isEmailValid;

    private boolean isUsernameValid;

    public PasswordRecoveryViewModel(@NonNull Navigator navigator,
                                     @NonNull AccountsRepository repository,
                                     @NonNull Validator validator) {
        super(navigator);
        mRepository = repository;
        mValidator = validator;
    }

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {

    }

    @Override
    public void restorePassword() {
        if (!isEmailValid) {
            getView().showError(R.string.invalid_email);
        } else if (!isUsernameValid) {
            getView().showError(R.string.invalid_username);
        } else {
            Subscription subscription = mRepository.restorePassword(email.get(), username.get())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @DebugLog
                        @Override
                        public void onError(Throwable e) {
                            getView().showError(e.getMessage());
                        }

                        @DebugLog
                        @Override
                        public void onNext(String password) {
                            getView().showPassword(password);
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

    public void validateEmail(Editable value) {
        String email = value.toString();
        if (mValidator.isValid(email, FieldType.Email)) {
            isEmailValid = true;
            return;
        }
        isEmailValid = false;
    }
}