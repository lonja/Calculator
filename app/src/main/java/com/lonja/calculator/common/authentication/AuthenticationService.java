package com.lonja.calculator.common.authentication;

import android.accounts.AccountManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lonja.calculator.data.repository.AccountsRepository;

import io.realm.Realm;

public class AuthenticationService extends Service {

    private CalculatorAccountAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        super.onCreate();
        AccountsRepository repository = AccountsRepository.getInstance(Realm.getDefaultInstance(),
                AccountManager.get(getApplicationContext()));
        AuthenticationManager manager = new AuthenticationManager(repository);
        mAuthenticator = new CalculatorAccountAuthenticator(getApplicationContext(), manager);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
