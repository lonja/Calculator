package com.lonja.calculator.common.authentication;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.lonja.calculator.data.entity.Account;
import com.lonja.calculator.data.entity.Token;
import com.lonja.calculator.data.repository.AccountsRepository;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import hugo.weaving.DebugLog;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.util.async.Async;

import static com.lonja.calculator.data.entity.Token.TokenType.FACEBOOK;
import static com.lonja.calculator.data.entity.Token.TokenType.GOOGLE;
import static com.lonja.calculator.data.entity.Token.TokenType.TWITTER;

//TODO 17.08.16 migrate to firebase auth
public class AuthenticationManager {

    private FragmentActivity mFragmentActivity;

    private CallbackManager mCallbackManager;

    private AccountsRepository mRepository;

    private PublishSubject<Account> mAccountPublishSubject;

    private FacebookCallback<LoginResult> mFacebookCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {
            Account account = new Account();
            Profile facebookProfile = Profile.getCurrentProfile();
            account.setFirstName(facebookProfile.getFirstName());
            account.setLastName(facebookProfile.getLastName());
            Token token = new Token();
            AccessToken facebookToken = loginResult.getAccessToken();
            token.setToken(facebookToken.getToken());
            token.setLastRefresh(facebookToken.getLastRefresh());
            token.setExpires(facebookToken.getExpires());
            token.setType(FACEBOOK);
            account.setToken(token);
            mAccountPublishSubject.onNext(account);
            mAccountPublishSubject.onCompleted();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {
            mAccountPublishSubject.onError(error);
        }
    };

    private Callback<TwitterSession> mTwitterCallback = new Callback<TwitterSession>() {

        @DebugLog
        @Override
        public void success(Result<TwitterSession> result) {
            Token token = new Token();
            TwitterAuthToken twitterToken = result.data.getAuthToken();
            token.setToken(twitterToken.token);
            token.setType(TWITTER);
            Account account = new Account();
            account.setUsername(result.data.getUserName());
            account.setToken(token);
            mAccountPublishSubject.onNext(account);
            mAccountPublishSubject.onCompleted();
        }

        @DebugLog
        @Override
        public void failure(TwitterException exception) {
            mAccountPublishSubject.onError(exception);
        }
    };

    private GoogleCallback<GoogleSignInResult> mGoogleCallback = new GoogleCallback<GoogleSignInResult>() {
        @Override
        public void onSuccess(GoogleSignInResult result) {
            GoogleSignInAccount googleAccount = result.getSignInAccount();
            Account account = new Account();
            account.setEmail(googleAccount.getEmail());
            account.setFirstName(googleAccount.getGivenName());
            account.setLastName(googleAccount.getFamilyName());
            Token token = new Token();
            token.setType(GOOGLE);
            token.setToken(googleAccount.getIdToken());
            account.setToken(token);
            mAccountPublishSubject.onNext(account);
            mAccountPublishSubject.onCompleted();
        }

        @Override
        public void onError(Throwable error) {
            mAccountPublishSubject.onError(error);
        }
    };

    public AuthenticationManager(@NonNull AccountsRepository repository) {

        mRepository = repository;
        mCallbackManager = new CallbackManager();
        mAccountPublishSubject = PublishSubject.create();
    }

    private AuthenticationManager() {

    }

    public void setFragmentActivity(@NonNull FragmentActivity activity) {
        mFragmentActivity = activity;
    }

    public Observable<Account> authenticate(@NonNull String username, @NonNull String password) {
        return mRepository.signIn(username, password)
                .flatMap(account -> mRepository.addAccountToAccountManager(account));
    }

    public Observable<Account> register(@NonNull Account account) {
        return mRepository.signUp(account);
    }

    @DebugLog
    public Observable<Account> authenticate(AuthenticationProvider provider) {
        BaseSocialAuthenticationStrategy strategy;
        switch (provider) {
            case Facebook:
                strategy = new FacebookAuthenticationStrategy(mFragmentActivity, mFacebookCallback);
                mCallbackManager.setStrategy(strategy);
                strategy.login();
                break;
            case Twitter:
                strategy = new TwitterAuthenticationStrategy(mFragmentActivity, mTwitterCallback);
                mCallbackManager.setStrategy(strategy);
                strategy.login();
                break;
            case Google:
                strategy = new GoogleAuthenticationStrategy(mFragmentActivity, mGoogleCallback);
                mCallbackManager.setStrategy(strategy);
                strategy.login();
                break;
        }
        return mAccountPublishSubject;
    }

    public Observable<Account> validateToken(Account account) {
        return Async.start(() -> {
            switch (account.getToken().getType()) {
                case FACEBOOK:
                    AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
                        @Override
                        public void OnTokenRefreshed(AccessToken accessToken) {
                            account.getToken().setToken(accessToken.getToken());
                            account.getToken().setExpires(accessToken.getExpires());
                            account.getToken().setLastRefresh(accessToken.getLastRefresh());
                        }

                        @Override
                        public void OnTokenRefreshFailed(FacebookException exception) {

                        }
                    });
                    break;
                case TWITTER:
                    break;
                case GOOGLE:
                    break;
            }
            return account;
        });
    }

    public Observable<Account> getAccountFromAccountManager() {
        return mRepository.getAccountFromAccountManager();
    }

    @DebugLog
    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }

    interface SocialAuthenticationStrategy {

        void login();
    }
}