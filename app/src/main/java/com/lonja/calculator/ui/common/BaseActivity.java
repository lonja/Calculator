package com.lonja.calculator.ui.common;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.lonja.calculator.BR;

public abstract class BaseActivity<B extends ViewDataBinding, VM extends ViewModel>
        extends AppCompatActivity {

    protected B binding;

    protected VM viewModel;

    protected final void setAndBindContentView(@LayoutRes int layoutId,
                                               @Nullable Bundle savedInstanceState) {
        if (viewModel == null) {
            throw new IllegalStateException("viewModel must not be null and should be injected via activityComponent().inject(this)");
        }
        binding = DataBindingUtil.setContentView(this, layoutId);
        binding.setVariable(BR.viewModel, viewModel);
        //noinspection unchecked
        viewModel.attachView((View) this, savedInstanceState);
    }

    @Override
    @CallSuper
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel != null) {
            viewModel.saveInstanceState(outState);
        }
    }

    @Override
    @CallSuper
    protected void onDestroy() {
        super.onDestroy();
        if (viewModel != null) {
            viewModel.detachView();
        }
//        if (realm != null) {
//            realm.close();
//        }
        binding = null;
        viewModel = null;
//        mActivityComponent = null;
//        realm = null;
    }

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }
}