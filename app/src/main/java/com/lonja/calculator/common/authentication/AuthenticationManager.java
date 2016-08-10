package com.lonja.calculator.common.authentication;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.lonja.calculator.common.authentication.data.Token;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

public class AuthenticationManager {

    private FragmentActivity mFragmentActivity;

    private Token facebookToken;

    private Token twitterToken;

    private Token googleToken;

    public AuthenticationManager(FragmentActivity activity) {
        mFragmentActivity = activity;
    }

    private AuthenticationManager() {

    }

    public void authenticate(String username, String password) {

    }

    public void authenticate(AuthenticationProvider provider) {
        switch (provider) {
            case Facebook:
                new FacebookAuthenticationStrategy(mFragmentActivity, new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        String token = loginResult.getAccessToken().getToken();
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {

                    }
                }).login();
                break;
            case Twitter:
                new TwitterAuthenticationStrategy(mFragmentActivity, new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        String token = result.data.getAuthToken().token;
                        Log.e("twitter token", token);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        Log.e("twitter error", exception.getMessage());
                    }
                }).login();
                break;
            case Google:
                new GoogleAuthenticationStrategy(mFragmentActivity).login();
                break;
        }

    }

    public CallbackManager getCallbackManager() {
        return null;
    }

    interface SocialAuthenticationStrategy {

        void login();
    }

    public class CallbackManager {

        static final int GOOGLE_RC = 456;

        static final int FACEBOOK_RC = 457;

        static final int TWITTER_RC = 455;

        public CallbackManager() {

        }

        public void onActivityResult(int requestCode, Intent data) {

            switch (requestCode) {
                case GOOGLE_RC:

                case FACEBOOK_RC:

                case TWITTER_RC:

            }
        }
    }
}