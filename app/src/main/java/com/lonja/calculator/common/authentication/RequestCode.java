package com.lonja.calculator.common.authentication;

import com.facebook.internal.CallbackManagerImpl;
import com.twitter.sdk.android.core.TwitterAuthConfig;

public enum RequestCode {

    Twitter, Facebook, Google;

    public static RequestCode get(int requestCode) {
        if (requestCode == TwitterAuthConfig.DEFAULT_AUTH_REQUEST_CODE) {
            return Twitter;
        } else if (requestCode == CallbackManagerImpl.RequestCodeOffset.Login.toRequestCode()) {
            return Facebook;
        } else if (requestCode == 9001) {
            return Google;
        } else {
            return null;
        }
    }
}
