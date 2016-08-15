package com.lonja.calculator.ui.login;

import android.accounts.AccountManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;

import com.lonja.calculator.R;
import com.lonja.calculator.common.authentication.AuthenticationManager;
import com.lonja.calculator.data.repository.AccountsRepository;
import com.lonja.calculator.databinding.ActivityLoginBinding;
import com.lonja.calculator.ui.common.BaseActivity;
import com.lonja.calculator.ui.common.navigator.ActivityNavigator;

import io.realm.Realm;


public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> implements LoginContract.View {

    public static final String EXTRA_TOKEN_TYPE = "EXTRA_TOKEN_TYPE";

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm realm = Realm.getDefaultInstance();
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Logging in");
        AccountsRepository repository = AccountsRepository.getInstance(realm, AccountManager.get(this));
        AuthenticationManager manager = new AuthenticationManager(this, repository);
        LoginViewModel viewModel = new LoginViewModel(new ActivityNavigator(this), manager, repository, this);
        setViewModel(viewModel);
        setAndBindContentView(R.layout.activity_login, savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        viewModel.onAuthenticationResult(requestCode, resultCode, data);
    }

    @Override
    public void showLoadingDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideLoadingDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void showLoginError(@NonNull String errorMessage) {
        new AlertDialog.Builder(this)
                .setMessage(errorMessage)
                .setCancelable(false)
                .setTitle("Error")
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }
}