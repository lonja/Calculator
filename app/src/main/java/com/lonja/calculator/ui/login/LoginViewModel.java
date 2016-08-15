package com.lonja.calculator.ui.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.lonja.calculator.common.authentication.AuthenticationManager;
import com.lonja.calculator.common.authentication.AuthenticationProvider;
import com.lonja.calculator.data.entity.Account;
import com.lonja.calculator.data.repository.AccountsRepository;
import com.lonja.calculator.ui.calculator.CalculatorActivity;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;
import com.lonja.calculator.ui.login.LoginContract.View;
import com.lonja.calculator.ui.login.LoginContract.ViewModel;
import com.lonja.calculator.ui.passwordrestore.PasswordRecoveryActivity;
import com.lonja.calculator.ui.registration.RegistrationActivity;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;


public class LoginViewModel extends BaseViewModel<View> implements ViewModel<View>,
        GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    public ObservableField<String> username = new ObservableField<>();
    public ObservableField<String> password = new ObservableField<>();

    private AuthenticationManager mManager;
    private AccountsRepository mAccountsRepository;

    private Context mContext;

    private CompositeSubscription mSubscriptions;

    protected LoginViewModel(@NonNull Navigator navigator,
                             @NonNull AuthenticationManager manager,
                             @NonNull AccountsRepository repository,
                             @NonNull Context context) {
        super(navigator);
        mManager = manager;
        mContext = context;
        mAccountsRepository = repository;
        mSubscriptions = new CompositeSubscription();
    }

    @Override
    public void loginWithFacebook() {
        mManager.authenticate(AuthenticationProvider.Facebook);
    }

    @Override
    public void loginWithTwitter() {
        mManager.authenticate(AuthenticationProvider.Twitter);
    }

    @Override
    public void loginWithGoogle() {
        mManager.authenticate(AuthenticationProvider.Google);
    }

    @Override
    public void login() {
        getView().showLoadingDialog();
        mManager.authenticate(username.get(), password.get())
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
    }

    @Override
    public void register() {
        navigator.startActivity(new Intent(mContext, RegistrationActivity.class));
    }

    @Override
    public void restorePassword() {
        navigator.startActivity(new Intent(mContext, PasswordRecoveryActivity.class));
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

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
