package com.lonja.calculator.common.authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import java.util.Collection;
import java.util.Collections;

class FacebookAuthenticationStrategy extends BaseSocialAuthenticationStrategy {

    private LoginManager mFacebookLoginManager;

    private CallbackManager mFacebookCallbackManager;

    private Collection<String> mFacebookPermissions;

    private FacebookCallback<LoginResult> mFacebookLoginResultCallback;

    FacebookAuthenticationStrategy(@NonNull FragmentActivity activity,
                                   @NonNull FacebookCallback<LoginResult> loginResultCallback) {
        super(activity);
        mFacebookLoginResultCallback = loginResultCallback;
        mFacebookLoginManager = LoginManager.getInstance();
        mFacebookCallbackManager = CallbackManager.Factory.create();
        mFacebookPermissions = Collections.singletonList("public_profile");
        mFacebookLoginManager.registerCallback(mFacebookCallbackManager, mFacebookLoginResultCallback);
    }

    @Override
    public void login() {
        mFacebookLoginManager.logInWithReadPermissions(fragmentActivity, mFacebookPermissions);
    }

    @Override
    public void executeCallbacks(int requestCode, int responseCode, Intent data) {
        mFacebookCallbackManager.onActivityResult(requestCode, responseCode, data);
    }
}

