package com.lonja.calculator.ui.common.navigator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class FragmentNavigator extends BaseNavigator {

    private final Fragment fragment;

    public FragmentNavigator(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    final FragmentActivity getActivity() {
        return fragment.getActivity();
    }

    @Override
    final FragmentManager getChildFragmentManager() {
        return fragment.getChildFragmentManager();
    }
}
