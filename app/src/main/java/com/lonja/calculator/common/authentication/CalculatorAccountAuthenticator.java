package com.lonja.calculator.common.authentication;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lonja.calculator.ui.login.LoginActivity;

import static com.lonja.calculator.data.entity.Token.TokenType.SERVER;


public class CalculatorAccountAuthenticator extends AbstractAccountAuthenticator {

    private Context mContext;

    private AuthenticationManager mAuthenticationManager;

    public CalculatorAccountAuthenticator(@NonNull Context context, @NonNull AuthenticationManager authenticationManager) {
        super(context);
        mContext = context;
        mAuthenticationManager = authenticationManager;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                 String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String accountType,
                             String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        final Intent intent = new Intent(mContext, LoginActivity.class);
        intent.putExtra(LoginActivity.EXTRA_TOKEN_TYPE, accountType);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        final Bundle bundle = new Bundle();
        if (options != null) {
            bundle.putAll(options);
        }
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                     Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse,
                               Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        final Bundle result = new Bundle();
        mAuthenticationManager.getAccountFromAccountManager()
                .flatMap(responseAccount -> {
                    switch (responseAccount.getToken().getType()) {
                        case SERVER:
                            return mAuthenticationManager.authenticate(responseAccount.getUsername(),
                                    responseAccount.getPassword());
                        default:
                            return mAuthenticationManager.validateToken(responseAccount);
                    }
                })
                .doOnNext(responseAccount -> {
                    result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
                    result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
                    result.putString(AccountManager.KEY_AUTHTOKEN, responseAccount.getToken().getToken());
                })
                .doOnError(throwable -> {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
                    result.putParcelable(AccountManager.KEY_INTENT, intent);
                })
                .subscribe();
        return result;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
                                    Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse,
                              Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
