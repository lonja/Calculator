package com.lonja.calculator.ui.calculator;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lonja.calculator.R;
import com.lonja.calculator.databinding.FragmentFactorialBinding;
import com.lonja.calculator.ui.common.BaseFragment;
import com.lonja.calculator.ui.common.navigator.FragmentNavigator;

import hugo.weaving.DebugLog;

public class FactorialFragment extends BaseFragment<FragmentFactorialBinding, FactorialViewModel>
        implements FactorialContract.View {

    @StringRes
    public static final int TITLE = R.string.factorial;

    private ProgressDialog mProgressDialog;

    public static FactorialFragment newInstance() {

        Bundle args = new Bundle();

        FactorialFragment fragment = new FactorialFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage("Calculating");
        mProgressDialog.setCancelable(false);
        setViewModel(new FactorialViewModel(new FragmentNavigator(this)));
        return setAndBindContentView(inflater, container, R.layout.fragment_factorial, savedInstanceState);
    }

    @Override
    public void showProgress() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgress() {
        mProgressDialog.dismiss();
    }
}