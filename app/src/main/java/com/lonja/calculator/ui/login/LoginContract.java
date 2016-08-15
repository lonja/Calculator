package com.lonja.calculator.ui.login;

import android.support.annotation.NonNull;

public interface LoginContract {

    interface View extends com.lonja.calculator.ui.common.View {

        void showLoadingDialog();

        void hideLoadingDialog();

        void showLoginError(@NonNull String errorMessage);
    }

    interface ViewModel<V extends View> extends com.lonja.calculator.ui.common.ViewModel<V> {

        void loginWithFacebook();

        void loginWithTwitter();

        void loginWithGoogle();

        void login();

        void register();

        void restorePassword();
    }

}
