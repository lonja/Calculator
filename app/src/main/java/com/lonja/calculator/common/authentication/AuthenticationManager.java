package com.lonja.calculator.common.authentication;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.lonja.calculator.common.authentication.data.Token;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import hugo.weaving.DebugLog;

public class AuthenticationManager {

    private FragmentActivity mFragmentActivity;

    private TwitterAuthenticationStrategy twitterAuthenticationStrategy;

    private FacebookAuthenticationStrategy facebookAuthenticationStrategy;

    private GoogleAuthenticationStrategy googleAuthenticationStrategy;

    private Token facebookToken;

    private Token twitterToken;

    private Token googleToken;

    private CallbackManager mCallbackManager;

    public AuthenticationManager(FragmentActivity activity) {
        mFragmentActivity = activity;
        mCallbackManager = new CallbackManager();
    }

    private AuthenticationManager() {

    }

    public void authenticate(String username, String password) {

    }

    public void authenticate(AuthenticationProvider provider) {
        switch (provider) {
            case Facebook:
                facebookAuthenticationStrategy = new FacebookAuthenticationStrategy(mFragmentActivity,
                        new FacebookCallback<LoginResult>() {
                            @DebugLog
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                String token = loginResult.getAccessToken().getToken();
                            }

                            @DebugLog
                            @Override
                            public void onCancel() {

                            }

                            @DebugLog
                            @Override
                            public void onError(FacebookException error) {

                            }
                        });
                facebookAuthenticationStrategy.login();
                break;
            case Twitter:
                twitterAuthenticationStrategy = new TwitterAuthenticationStrategy(mFragmentActivity,
                        new Callback<TwitterSession>() {
                            @DebugLog
                            @Override
                            public void success(Result<TwitterSession> result) {
                                String token = result.data.getAuthToken().token;
                                Log.e("twitter token", token);
                            }

                            @DebugLog
                            @Override
                            public void failure(TwitterException exception) {
                                Log.e("twitter error", exception.getMessage());
                            }
                        });
                twitterAuthenticationStrategy.login();
                break;
            case Google:
                googleAuthenticationStrategy = new GoogleAuthenticationStrategy(mFragmentActivity,
                        new GoogleCallback<GoogleSignInResult>() {
                            @DebugLog
                            @Override
                            public void onSuccess(GoogleSignInResult result) {
                                Log.e("google account", result.getSignInAccount().getDisplayName());
                            }

                            @DebugLog
                            @Override
                            public void onError(Throwable error) {

                            }
                        });
                googleAuthenticationStrategy.login();
                break;
        }

    }

    public CallbackManager getCallbackManager() {
        return mCallbackManager;
    }

    interface SocialAuthenticationStrategy {

        void login();
    }

    public class CallbackManager {

        public CallbackManager() {

        }

        public void onAuthenticationResult(int requestCode, int resultCode, Intent data) {
            RequestCode code = RequestCode.get(requestCode);
            if (code == null) {
                throw new IllegalStateException("Wrong request code passed into CallbackManager");
            }
            switch (code) {
                case Google:
                    googleAuthenticationStrategy.executeCallbacks(requestCode, resultCode, data);
                    break;
                case Facebook:
                    facebookAuthenticationStrategy.executeCallbacks(requestCode, resultCode, data);
                    break;
                case Twitter:
                    twitterAuthenticationStrategy.executeCallbacks(requestCode, resultCode, data);
                    break;
            }
        }
    }
}