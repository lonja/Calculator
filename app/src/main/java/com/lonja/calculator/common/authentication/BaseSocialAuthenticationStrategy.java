package com.lonja.calculator.common.authentication;

import android.support.v4.app.FragmentActivity;

abstract class BaseSocialAuthenticationStrategy
        implements AuthenticationManager.SocialAuthenticationStrategy {

    FragmentActivity fragmentActivity;

    BaseSocialAuthenticationStrategy(FragmentActivity activity) {
        fragmentActivity = activity;
    }
}