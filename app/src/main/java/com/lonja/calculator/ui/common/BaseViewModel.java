package com.lonja.calculator.ui.common;

import android.databinding.BaseObservable;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.lonja.calculator.ui.common.navigator.Navigator;

import rx.subscriptions.CompositeSubscription;

public abstract class BaseViewModel<V extends View> extends BaseObservable implements ViewModel<V> {

    protected Navigator navigator;

    protected CompositeSubscription subscriptions = new CompositeSubscription();

    private V mView;

    protected BaseViewModel(@NonNull Navigator navigator) {
        this.navigator = navigator;
    }

    public V getView() {
        return mView;
    }

    @Override
    @CallSuper
    public void attachView(@NonNull V view, @Nullable Bundle savedInstanceState) {
        mView = view;
        if (savedInstanceState != null) {
            restoreInstanceState(savedInstanceState);
        }
    }

    @Override
    @CallSuper
    public void detachView() {
        mView = null;
        subscriptions = null;
    }

}
