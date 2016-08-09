package com.lonja.calculator.common.authentication;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.lonja.calculator.ui.login.LoginActivity;


public class CalculatorAccountAuthenticator extends AbstractAccountAuthenticator {

    private Context mContext;

    public CalculatorAccountAuthenticator(Context context) {
        super(context);
        mContext = context;
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
        final AccountManager am = AccountManager.get(mContext.getApplicationContext());
        String authToken = am.peekAuthToken(account, authTokenType);
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (!TextUtils.isEmpty(password)) {
//                authToken = AuthTokenLoader.signIn(mContext, account.name, password);
            }
        }
        if (!TextUtils.isEmpty(authToken)) {
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        } else {
            final Intent intent = new Intent(mContext, LoginActivity.class);
            intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
            intent.putExtra(LoginActivity.EXTRA_TOKEN_TYPE, authTokenType);
            final Bundle bundle = new Bundle();
            bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        }
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
