package com.lonja.calculator.ui.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;

import com.lonja.calculator.R;
import com.lonja.calculator.common.authentication.AuthenticationManager;
import com.lonja.calculator.common.authentication.AuthenticationProvider;
import com.lonja.calculator.data.entity.Account;
import com.lonja.calculator.data.repository.AccountsRepository;
import com.lonja.calculator.domain.validation.FieldType;
import com.lonja.calculator.domain.validation.Validator;
import com.lonja.calculator.ui.calculator.CalculatorActivity;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;
import com.lonja.calculator.ui.login.LoginContract.View;
import com.lonja.calculator.ui.login.LoginContract.ViewModel;
import com.lonja.calculator.ui.passwordrestore.PasswordRecoveryActivity;
import com.lonja.calculator.ui.registration.RegistrationActivity;

import hugo.weaving.DebugLog;
import rx.Subscriber;
import rx.Subscription;


public class LoginViewModel extends BaseViewModel<View> implements ViewModel<View> {

    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    private AuthenticationManager mManager;
    private AccountsRepository mAccountsRepository;

    private Context mContext;

    private Validator mValidator;

    private boolean isUsernameValid;
    private boolean isPasswordValid;

    protected LoginViewModel(@NonNull Navigator navigator,
                             @NonNull AuthenticationManager manager,
                             @NonNull AccountsRepository repository,
                             @NonNull Validator validator,
                             @NonNull Context context) {
        super(navigator);
        mManager = manager;
        mContext = context;
        mAccountsRepository = repository;
        mValidator = validator;
    }

    @Override
    public void loginWithFacebook() {
        Subscription subscription = mManager.authenticate(AuthenticationProvider.Facebook)
                .flatMap(account -> mAccountsRepository.addAccountToAccountManager(account))
                .subscribe(new Subscriber<Account>() {
                    @DebugLog
                    @Override
                    public void onCompleted() {
                        navigator.startActivityWithClosing(CalculatorActivity.class);
                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        getView().showLoginError(e.getMessage());
                    }

                    @DebugLog
                    @Override
                    public void onNext(Account account) {

                    }
                });
        subscriptions.add(subscription);
    }

    @Override
    public void loginWithTwitter() {
        Subscription subscription = mManager.authenticate(AuthenticationProvider.Twitter)
                .flatMap(account -> mAccountsRepository.addAccountToAccountManager(account))
                .subscribe(new Subscriber<Account>() {
                    @Override
                    public void onCompleted() {
                        navigator.startActivityWithClosing(CalculatorActivity.class);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showLoginError(e.getMessage());
                    }

                    @Override
                    public void onNext(Account account) {

                    }
                });
        subscriptions.add(subscription);
    }

    @Override
    public void loginWithGoogle() {
        Subscription subscription = mManager.authenticate(AuthenticationProvider.Google)
                .flatMap(account -> mAccountsRepository.addAccountToAccountManager(account))
                .subscribe(new Subscriber<Account>() {

                    @DebugLog
                    @Override
                    public void onCompleted() {
                        navigator.startActivityWithClosing(CalculatorActivity.class);
                    }

                    @DebugLog
                    @Override
                    public void onError(Throwable e) {
                        getView().showLoginError(e.getMessage());
                    }

                    @DebugLog
                    @Override
                    public void onNext(Account account) {

                    }
                });
        subscriptions.add(subscription);
    }

    @Override
    public void login() {
        if (!isUsernameValid) {
            getView().showValidationError(R.string.invalid_username);
        } else if (!isPasswordValid) {
            getView().showValidationError(R.string.invalid_password);
        } else {
            getView().showLoadingDialog();
            Subscription subscription = mManager.authenticate(username.get(), password.get())
                    .subscribe(new Subscriber<Account>() {
                        @Override
                        public void onCompleted() {
                            getView().hideLoadingDialog();
                            navigator.startActivityWithClosing(CalculatorActivity.class);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getView().showLoginError(e.getMessage());
                        }

                        @Override
                        public void onNext(Account account) {

                        }
                    });
            subscriptions.add(subscription);
        }
    }

    @Override
    public void register() {
        navigator.startActivity(new Intent(mContext, RegistrationActivity.class));
    }

    @Override
    public void restorePassword() {
        navigator.startActivity(new Intent(mContext, PasswordRecoveryActivity.class));
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

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {

    }

    public void onAuthenticationResult(int requestCode, int resultCode, Intent data) {
        mManager.getCallbackManager().onAuthenticationResult(requestCode, resultCode, data);
    }
}
