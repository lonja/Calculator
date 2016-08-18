package com.lonja.calculator.ui.splashscreen;

import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.lonja.calculator.R;
import com.lonja.calculator.databinding.ActivitySplashBinding;
import com.lonja.calculator.ui.common.BaseActivity;
import com.lonja.calculator.ui.common.navigator.ActivityNavigator;
import com.tbruyelle.rxpermissions.RxPermissions;

import hugo.weaving.DebugLog;

public class SplashScreenActivity extends BaseActivity<ActivitySplashBinding, CheckAccountViewModel> implements SplashScreenContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViewModel(new CheckAccountViewModel(new ActivityNavigator(this),
                RxPermissions.getInstance(this),
                AccountManager.get(this)));
        setAndBindContentView(R.layout.activity_splash, savedInstanceState);
        viewModel.checkAccount();
    }

    @DebugLog
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showErrorPermissionCheck() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false)
                .setMessage(R.string.message_permission_denied)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    viewModel.checkAccount();
                }).show();
    }
}