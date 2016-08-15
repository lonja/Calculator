package com.lonja.calculator.ui.calculator;

public interface FactorialContract {

    interface View extends com.lonja.calculator.ui.common.View {

        void showProgress();

        void hideProgress();
    }

    interface ViewModel extends com.lonja.calculator.ui.common.ViewModel<View> {

    }
}
