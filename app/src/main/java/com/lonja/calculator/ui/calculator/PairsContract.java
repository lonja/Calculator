package com.lonja.calculator.ui.calculator;

import android.support.annotation.NonNull;

public interface PairsContract {

    interface View extends com.lonja.calculator.ui.common.View {

        void showValidationError(@NonNull String message);
    }

    interface ViewModel extends com.lonja.calculator.ui.common.ViewModel<View> {

    }
}
