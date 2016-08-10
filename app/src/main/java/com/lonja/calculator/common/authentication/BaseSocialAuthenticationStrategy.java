package com.lonja.calculator.common.authentication;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

abstract class BaseSocialAuthenticationStrategy
        implements AuthenticationManager.SocialAuthenticationStrategy {

    protected FragmentActivity fragmentActivity;

    BaseSocialAuthenticationStrategy(FragmentActivity activity) {
        fragmentActivity = activity;
    }

    public abstract void executeCallbacks(int requestCode, int responseCode, Intent data);
}