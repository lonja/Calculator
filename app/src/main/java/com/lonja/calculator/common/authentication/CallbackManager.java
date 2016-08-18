package com.lonja.calculator.common.authentication;

import android.content.Intent;

public class CallbackManager {

    private BaseSocialAuthenticationStrategy mStrategy;

    public CallbackManager() {

    }

    public void setStrategy(BaseSocialAuthenticationStrategy strategy) {
        mStrategy = strategy;
    }

    public void onAuthenticationResult(int requestCode, int resultCode, Intent data) {
        RequestCode code = RequestCode.get(requestCode);
        if (code == null) {
            throw new IllegalStateException("Wrong request code passed into CallbackManager");
        }
        mStrategy.executeCallbacks(requestCode, resultCode, data);
    }
}
