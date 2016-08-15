package com.lonja.calculator.ui.common;

import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;

public interface ViewModel<V extends View> extends Observable {

    void attachView(V view, Bundle savedInstanceState);

    void detachView();

    void saveInstanceState(@NonNull Bundle outState);

    void restoreInstanceState(@NonNull Bundle savedInstanceState);

}