package com.lonja.calculator.ui.calculator;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.lonja.calculator.common.util.PalindromeHelper;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;

import hugo.weaving.DebugLog;

public class PalindromeViewModel extends BaseViewModel<PalindromeFragment> {

    private static final String KEY_VALUE = "value";

    private static final String KEY_IS_PALINDROME = "result";

    public final ObservableField<Integer> value = new ObservableField<>();

    public final ObservableField<Boolean> isPalindrome = new ObservableField<>();

    @DebugLog
    protected PalindromeViewModel(@NonNull Navigator navigator) {
        super(navigator);
    }

    @DebugLog
    public void calculate() {
        isPalindrome.set(PalindromeHelper.isPalindrome(value.get()));
    }

    @DebugLog
    @Override
    public void saveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(KEY_IS_PALINDROME, isPalindrome.get());
        outState.putInt(KEY_VALUE, value.get());
    }

    @DebugLog
    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {
        isPalindrome.set(savedInstanceState.getBoolean(KEY_IS_PALINDROME));
        value.set(savedInstanceState.getInt(KEY_VALUE));
    }
}
