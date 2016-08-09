package com.lonja.calculator.common.authentication;

import android.support.v4.app.FragmentActivity;

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

    public void login(String username, String password) {

    }

    public void login(AuthenticationProvider provider) {
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
            case Twitter:
                new TwitterAuthenticationStrategy(mFragmentActivity, new Callback<TwitterSession>() {
                    @Override
                    public void success(Result<TwitterSession> result) {
                        //TODO 09.08.16 get access token here
                    }

                    @Override
                    public void failure(TwitterException exception) {

                    }
                }).login();
            case Google:
                new GoogleAuthenticationStrategy(mFragmentActivity).login();
        }

    }

    interface SocialAuthenticationStrategy {

        void login();
    }
}