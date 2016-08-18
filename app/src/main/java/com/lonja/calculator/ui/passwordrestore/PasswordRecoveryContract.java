package com.lonja.calculator.ui.passwordrestore;


import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public interface PasswordRecoveryContract {

    interface View extends com.lonja.calculator.ui.common.View {

        void showPassword(@NonNull String password);

        void showError(@NonNull String message);

        void showError(@StringRes int message);

    }

    interface ViewModel<V extends View> extends com.lonja.calculator.ui.common.ViewModel<V> {

        void restorePassword();
    }
}
