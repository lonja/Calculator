package com.lonja.calculator.ui.calculator;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lonja.calculator.common.util.FactorialHelper;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;

import hugo.weaving.DebugLog;
import rx.android.schedulers.AndroidSchedulers;
import rx.util.async.Async;

public class FactorialViewModel extends BaseViewModel<FactorialContract.View>
        implements FactorialContract.ViewModel {

    private static final String KEY_VALUE = "value";

    private static final String KEY_RESULT = "result";

    public final ObservableField<Integer> value = new ObservableField<>();

    public final ObservableField<Integer> result = new ObservableField<>();

    @DebugLog
    protected FactorialViewModel(@NonNull Navigator navigator) {
        super(navigator);
    }

    @DebugLog
    public void calculate() {
        Async.start(() -> FactorialHelper.findFactorialDigitsSum(value.get()))
                .doOnSubscribe(() -> {
                    if (value.get() < 1000) {
                        return;
                    }
                    getView().showProgress();
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .doOnNext(result::set)
                .doOnCompleted(getView()::hideProgress)
                .subscribe();
    }

    @DebugLog
    @Override
    public void saveInstanceState(@NonNull Bundle outState) {
        outState.putInt(KEY_RESULT, result.get());
        outState.putInt(KEY_VALUE, value.get());
    }

    @DebugLog
    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {
        result.set(savedInstanceState.getInt(KEY_RESULT));
        value.set(savedInstanceState.getInt(KEY_VALUE));
    }
}
