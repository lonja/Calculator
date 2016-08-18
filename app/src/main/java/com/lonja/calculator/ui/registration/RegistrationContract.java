package com.lonja.calculator.ui.registration;


import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

public interface RegistrationContract {

    interface View extends com.lonja.calculator.ui.common.View {

        void showRegistrationError(@NonNull String message);

        void showValidationError(@NonNull String message);

        void showValidationError(@StringRes int message);

        void showRegistrationCompleted();
    }

    interface ViewModel<V extends View> extends com.lonja.calculator.ui.common.ViewModel<V> {

        void register();
    }
}
