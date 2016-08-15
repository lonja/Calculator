package com.lonja.calculator.ui.common.navigator;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class ActivityNavigator extends BaseNavigator {

    private final FragmentActivity activity;

    public ActivityNavigator(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    final FragmentActivity getActivity() {
        return activity;
    }

    @Override
    final FragmentManager getChildFragmentManager() {
        throw new UnsupportedOperationException("Activities do not have a child fragment manager.");
    }
}