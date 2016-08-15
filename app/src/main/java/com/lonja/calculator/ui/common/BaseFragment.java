package com.lonja.calculator.ui.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lonja.calculator.BR;

public abstract class BaseFragment<B extends ViewDataBinding, VM extends ViewModel> extends Fragment {

    protected B binding;

    protected VM viewModel;

    protected final android.view.View setAndBindContentView(@NonNull LayoutInflater inflater,
                                                            @Nullable ViewGroup container,
                                                            @LayoutRes int layoutResId,
                                                            Bundle savedInstanceState) {
        if (viewModel == null) {
            throw new IllegalStateException("viewModel must not be null and should be injected via fragmentComponent().inject(this)");
        }
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false);
        binding.setVariable(BR.viewModel, viewModel);
        //noinspection unchecked
        viewModel.attachView((View) this, savedInstanceState);
        return binding.getRoot();
    }

    @Override
    @CallSuper
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            viewModel.saveInstanceState(outState);
        }
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        super.onDestroyView();
        if (viewModel != null) {
            viewModel.detachView();
        }
        binding = null;
        viewModel = null;
    }

    @Override
    @CallSuper
    public void onDestroy() {
        super.onDestroy();
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }


}
