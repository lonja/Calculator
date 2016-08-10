package com.lonja.calculator.common.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

class TwitterAuthenticationStrategy extends BaseSocialAuthenticationStrategy {

    private TwitterAuthClient mTwitterAuthClient;

    private Callback<TwitterSession> mTwitterSessionCallback;

    TwitterAuthenticationStrategy(@NonNull FragmentActivity activity,
                                  @NonNull Callback<TwitterSession> twitterSessionCallback) {
        super(activity);
        mTwitterSessionCallback = twitterSessionCallback;
        mTwitterAuthClient = new TwitterAuthClient();
    }

    @Override
    public void login() {
        mTwitterAuthClient.authorize(fragmentActivity, mTwitterSessionCallback);
    }

    @Override
    public void executeCallbacks(int requestCode, int responseCode, Intent data) {
        mTwitterAuthClient.onActivityResult(requestCode, requestCode, data);
    }
}