package com.lonja.calculator.ui.splashscreen;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lonja.calculator.ui.calculator.CalculatorActivity;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.io.IOException;

import rx.Subscription;

import static com.lonja.calculator.data.repository.AccountsRepository.ACCOUNT_TYPE;

public class CheckAccountViewModel extends BaseViewModel<SplashScreenContract.View> {

    private RxPermissions mRxPermissions;

    private AccountManager mManager;

    public CheckAccountViewModel(@NonNull Navigator navigator,
                                 @NonNull RxPermissions rxPermissions,
                                 @NonNull AccountManager manager) {
        super(navigator);
        mRxPermissions = rxPermissions;
        mManager = manager;
    }

    public void checkAccount() {
        Subscription subscription = mRxPermissions.request(Manifest.permission.GET_ACCOUNTS)
                .subscribe(granted -> {
                    if (!granted) {
                        getView().showErrorPermissionCheck();
                        return;
                    }
                    Account[] accounts = mManager.getAccountsByType(ACCOUNT_TYPE);
                    if (accounts.length != 0) {
                        Account account = accounts[0];
                        mManager.getAuthToken(account, ACCOUNT_TYPE, null, true, future -> {
                            try {
                                Bundle bundle = future.getResult();
                                String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);
                                Log.e("getToken", "callback");
                                navigator.startActivityWithClosing(CalculatorActivity.class);
                            } catch (OperationCanceledException | IOException | AuthenticatorException ignored) {

                            }
                        }, null);
                        return;
                    }
                    navigator.finish();
                    mManager.addAccount(ACCOUNT_TYPE, ACCOUNT_TYPE, null, null, (Activity) getView(), accountManagerFuture -> {

                    }, new Handler());
                });
        subscriptions.add(subscription);
    }

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {

    }
}
