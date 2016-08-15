package com.lonja.calculator.ui.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lonja.calculator.R;
import com.lonja.calculator.databinding.FragmentPairsBinding;
import com.lonja.calculator.ui.common.BaseFragment;
import com.lonja.calculator.ui.common.navigator.FragmentNavigator;

import hugo.weaving.DebugLog;

public class PairsFragment extends BaseFragment<FragmentPairsBinding, PairsViewModel>
        implements com.lonja.calculator.ui.common.View {

    @StringRes
    public static final int TITLE = R.string.pairs;

    public static PairsFragment newInstance() {

        Bundle args = new Bundle();

        PairsFragment fragment = new PairsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @DebugLog
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @DebugLog
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setViewModel(new PairsViewModel(new FragmentNavigator(this)));
        return setAndBindContentView(inflater, container, R.layout.fragment_pairs, savedInstanceState);
    }
}
