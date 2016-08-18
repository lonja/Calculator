package com.lonja.calculator.ui.passwordrestore;

import android.accounts.AccountManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;

import com.lonja.calculator.R;
import com.lonja.calculator.data.repository.AccountsRepository;
import com.lonja.calculator.databinding.ActivityPasswordRecoveryBinding;
import com.lonja.calculator.domain.validation.Validator;
import com.lonja.calculator.ui.common.BaseActivity;
import com.lonja.calculator.ui.common.navigator.ActivityNavigator;

public class PasswordRecoveryActivity extends BaseActivity<ActivityPasswordRecoveryBinding, PasswordRecoveryViewModel>
        implements PasswordRecoveryContract.View {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        AccountsRepository repository = AccountsRepository.getInstance(realm, AccountManager.get(this));
        Validator validator = new Validator();
        PasswordRecoveryViewModel viewModel = new PasswordRecoveryViewModel(new ActivityNavigator(this),
                repository, validator);
        setViewModel(viewModel);
        setAndBindContentView(R.layout.activity_password_recovery, savedInstanceState);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showPassword(@NonNull String password) {
        Snackbar.make(binding.getRoot(), "Your password: " + password, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.copy, view -> {
                    ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    manager.setPrimaryClip(ClipData.newPlainText("password", password));
                })
                .show();
    }

    @Override
    public void showError(@NonNull String message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showError(@StringRes int message) {
        Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
    }
}
