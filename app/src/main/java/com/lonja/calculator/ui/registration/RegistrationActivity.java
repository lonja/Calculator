package com.lonja.calculator.ui.registration;

import android.accounts.AccountManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;

import com.lonja.calculator.R;
import com.lonja.calculator.common.authentication.AuthenticationManager;
import com.lonja.calculator.data.repository.AccountsRepository;
import com.lonja.calculator.databinding.ActivityRegistrationBinding;
import com.lonja.calculator.domain.validation.Validator;
import com.lonja.calculator.ui.common.BaseActivity;
import com.lonja.calculator.ui.common.navigator.ActivityNavigator;

public class RegistrationActivity extends BaseActivity<ActivityRegistrationBinding, RegistrationViewModel>
        implements RegistrationContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AccountsRepository repository = AccountsRepository.getInstance(realm, AccountManager.get(this));
        AuthenticationManager manager = new AuthenticationManager(repository);
        manager.setFragmentActivity(this);
        RegistrationViewModel viewModel = new RegistrationViewModel(new ActivityNavigator(this),
                this, manager, new Validator());
        setViewModel(viewModel);
        setAndBindContentView(R.layout.activity_registration, savedInstanceState);
    }

    @Override
    public void showRegistrationError(@NonNull String message) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.registration_error)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    @Override
    public void showValidationError(@NonNull String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showValidationError(@StringRes int message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showRegistrationCompleted() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.congrats)
                .setMessage(R.string.can_login)
                .setPositiveButton(R.string.ok, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    finish();
                }).show();
    }
}
