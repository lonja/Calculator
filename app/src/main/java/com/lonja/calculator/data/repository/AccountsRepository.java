package com.lonja.calculator.data.repository;

import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.lonja.calculator.data.entity.Account;
import com.lonja.calculator.data.entity.Token;
import com.lonja.calculator.data.exception.EmailException;
import com.lonja.calculator.data.exception.PasswordException;
import com.lonja.calculator.data.exception.UsernameException;

import java.util.Date;
import java.util.Objects;

import hugo.weaving.DebugLog;
import io.realm.Realm;
import rx.Observable;

public class AccountsRepository {

    public static final String ACCOUNT_TYPE = "com.lonja.calculator";

    private static AccountsRepository INSTANCE;

    private Realm mRealm;

    private AccountManager mAccountManager;

    private AccountsRepository(@NonNull Realm realm,
                               @NonNull AccountManager accountManager) {
        mRealm = realm;
        mAccountManager = accountManager;
    }

    public static AccountsRepository getInstance(@NonNull Realm realm,
                                                 @NonNull AccountManager accountManager) {
        if (INSTANCE == null) {
            INSTANCE = new AccountsRepository(realm, accountManager);
        }
        return INSTANCE;
    }

    public Observable<Account> signIn(@NonNull String username, @NonNull String password) {
        return getAccount(username, password)
                .map(account -> {
                    if (account.getToken() == null) {
                        Token token = Token.generateToken(account, Token.KEY);
                        startTransaction();
                        Token realmToken = mRealm.createObject(Token.class);
                        realmToken.setType(token.getType());
                        realmToken.setLastRefresh(token.getLastRefresh());
                        realmToken.setExpires(token.getExpires());
                        realmToken.setToken(token.getToken());
                        account.setToken(realmToken);
                        commitTransaction();
                    }
                    return account;
                });
    }

    public Observable<Account> signUp(@NonNull Account account) {
        return addAccount(account);
    }

    private Observable<Account> addAccount(@NonNull Account account) {
        return Observable.create(subscriber -> {
            Account realmAccount = mRealm.where(Account.class)
                    .equalTo("username", account.getUsername())
                    .findFirst();
            if (realmAccount != null) {
                throw new UsernameException("Account with this username already exists");
            }
            try {
                mRealm.beginTransaction();
                Account copiedRealmAccount = mRealm.copyToRealm(account);
                mRealm.commitTransaction();
                subscriber.onNext(copiedRealmAccount);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }

    private Observable<Account> getAccount(@NonNull String username, @NonNull String password) {
        return Observable.fromCallable(() -> mRealm.where(Account.class)
                .equalTo("username", username)
                .findFirst())
                .map(account -> {
                    if (account == null) {
                        throw new UsernameException("Cannot find user with username " + username);
                    } else if (!Objects.equals(account.getPassword(), password)) {
                        throw new PasswordException("Password is incorrect");
                    }
                    return account;
                });
    }

    @DebugLog
    public Observable<String> restorePassword(@NonNull String email, @NonNull String username) {
        return Observable.fromCallable(() -> mRealm.where(Account.class)
                .equalTo("username", username)
                .findFirst())
                .map(account -> {
                    if (account == null) {
                        throw new UsernameException("Cannot find username");
                    }
                    if (!Objects.equals(account.getEmail(), email)) {
                        throw new EmailException("Email is invalid");
                    }
                    return account.getPassword();
                });
    }

    public Observable<Account> getAccountFromAccountManager() {
        return Observable.create(subscriber -> {
            try {
                android.accounts.Account account = mAccountManager.getAccountsByType(ACCOUNT_TYPE)[0];
                Account user = new Account();
                user.setId(mAccountManager.getUserData(account, Account.ID));
                user.setUsername(mAccountManager.getUserData(account, Account.USERNAME));
                user.setFirstName(mAccountManager.getUserData(account, Account.FIRST_NAME));
                user.setLastName(mAccountManager.getUserData(account, Account.LAST_NAME));
                user.setEmail(mAccountManager.getUserData(account, Account.EMAIL));
                user.setPhoneNumber(mAccountManager.getUserData(account, Account.PHONE_NUMBER));
                user.setPassword(mAccountManager.getPassword(account));

                Token token = new Token();
                token.setToken(mAccountManager.getUserData(account, Account.TOKEN));
                String tokenExpiresString = mAccountManager.getUserData(account,
                        Account.TOKEN_EXPIRES_DATE);
                if (!TextUtils.isEmpty(tokenExpiresString)) {
                    token.setExpires(new Date(Long.parseLong(tokenExpiresString)));
                }
                String tokenLastRefreshString = mAccountManager.getUserData(account,
                        Account.TOKEN_REFRESH_DATE);
                if (!TextUtils.isEmpty(tokenLastRefreshString)) {
                    token.setLastRefresh(new Date(Long.parseLong(tokenLastRefreshString)));
                }
                token.setType(mAccountManager.getUserData(account, Account.TOKEN_TYPE));

                user.setToken(token);

                subscriber.onNext(user);
                subscriber.onCompleted();
            } catch (SecurityException | IndexOutOfBoundsException e) {
                subscriber.onError(e);
            }
        });
    }

    public Observable<Account> addAccountToAccountManager(Account account) {
        return Observable.create(subscriber -> {
            android.accounts.Account user = null;
            if (account.getUsername() != null) {
                user = new android.accounts.Account(account.getUsername(), ACCOUNT_TYPE);
            } else if (account.getFirstName() != null) {
                user = new android.accounts.Account(account.getFirstName() + " " + account.getLastName(),
                        ACCOUNT_TYPE);
            } else if (account.getEmail() != null) {
                user = new android.accounts.Account(account.getEmail(), ACCOUNT_TYPE);
            }
            Bundle userData = new Bundle();
            userData.putString(Account.ID, account.getId());
            userData.putString(Account.FIRST_NAME, account.getFirstName());
            userData.putString(Account.LAST_NAME, account.getLastName());
            userData.putString(Account.EMAIL, account.getEmail());
            userData.putString(Account.PHONE_NUMBER, account.getPhoneNumber());
            if (account.getToken().getExpires() != null) {
                userData.putString(Account.TOKEN_EXPIRES_DATE, Long.toString(account.getToken().getExpires().getTime()));
            }
            if (account.getToken().getLastRefresh() != null) {
                userData.putString(Account.TOKEN_REFRESH_DATE, Long.toString(account.getToken().getLastRefresh().getTime()));
            }
            userData.putString(Account.TOKEN_TYPE, account.getToken().getType());
            boolean isSuccess = mAccountManager.addAccountExplicitly(user, account.getPassword(),
                    userData);
            mAccountManager.setAuthToken(user, ACCOUNT_TYPE, account.getToken().getToken());
            if (isSuccess) {
                subscriber.onNext(account);
                subscriber.onCompleted();
                return;
            }
            subscriber.onError(new IllegalStateException("Cannot add this account to AccountManager"));
        });
    }

    private void startTransaction() {
        if (mRealm.isInTransaction()) {
            return;
        }
        mRealm.beginTransaction();
    }

    private void commitTransaction() {
        if (!mRealm.isInTransaction()) {
            return;
        }
        mRealm.commitTransaction();
    }
}