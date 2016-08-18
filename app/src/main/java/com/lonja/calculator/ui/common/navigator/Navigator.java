package com.lonja.calculator.ui.common.navigator;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public interface Navigator {

    String EXTRA_ARGS = "_args";

    void startActivity(@NonNull Intent intent);

    void startActivity(@NonNull String action);

    void startActivity(@NonNull String action, @NonNull Uri uri);

    void startActivity(@NonNull Class<? extends Activity> activityClass);

    void startActivity(@NonNull Class<? extends Activity> activityClass, Bundle args);

    void startActivity(@NonNull Class<? extends Activity> activityClass, Parcelable args);

    void startActivityForResult(@NonNull Intent intent, int requestCode);

    void startActivityForResult(@NonNull Intent intent, int requestCode, Bundle args);

    void startActivityWithClosing(@NonNull Class<? extends Activity> activityClass);

    void finish();

    void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment, Bundle args);

    void replaceFragment(@IdRes int containerId, @NonNull Fragment fragment,
                         @NonNull String fragmentTag, Bundle args);

    void replaceFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment,
                                          Bundle args, String backStackTag);

    void replaceFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment,
                                          @NonNull String fragmentTag, Bundle args,
                                          String backStackTag);

    void replaceChildFragment(@IdRes int containerId, @NonNull Fragment fragment, Bundle args);

    void replaceChildFragment(@IdRes int containerId, @NonNull Fragment fragment,
                              @NonNull String fragmentTag, Bundle args);

    void replaceChildFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment,
                                               Bundle args, String backStackTag);

    void replaceChildFragmentAndAddToBackStack(@IdRes int containerId, @NonNull Fragment fragment,
                                               @NonNull String fragmentTag, Bundle args,
                                               String backStackTag);
}
