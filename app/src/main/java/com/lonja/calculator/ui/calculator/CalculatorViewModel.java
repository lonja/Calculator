package com.lonja.calculator.ui.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;

public class CalculatorViewModel extends BaseViewModel<CalculatorActivity> {

    protected CalculatorViewModel(@NonNull Navigator navigator) {
        super(navigator);
    }

    @Override
    public void saveInstanceState(@NonNull Bundle outState) {

    }

    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {

    }
}
