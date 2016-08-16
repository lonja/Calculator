package com.lonja.calculator.ui.login;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public interface LoginContract {

    interface View extends com.lonja.calculator.ui.common.View {

        void showValidationError(@NonNull String message);

        void showValidationError(@StringRes int message);

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
