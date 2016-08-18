package com.lonja.calculator.ui.calculator;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Pair;

import com.lonja.calculator.common.IntegerPairSequence;
import com.lonja.calculator.domain.validation.FieldType;
import com.lonja.calculator.domain.validation.Validator;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

public class PairsViewModel extends BaseViewModel<PairsContract.View> implements PairsContract.ViewModel {

    private static final String KEY_VALUE = "value";

    private static final String KEY_RESULT = "result";

    public final ObservableField<String> value = new ObservableField<>();

    public final ObservableField<String> result = new ObservableField<>();

    private Validator mValidator;

    private boolean isPairsValid;

    public PairsViewModel(@NonNull Navigator navigator, @NonNull Validator validator) {
        super(navigator);
        mValidator = validator;
    }

    @DebugLog
    public void calculate() {
        if (!isPairsValid) {
            getView().showValidationError("You can only use digits and spaces in this field");
            return;
        }
        ArrayList<Pair<Integer, Integer>> pairs = parsePairs(value.get());
        IntegerPairSequence sequence = new IntegerPairSequence(pairs);
        ArrayList<Pair<Integer, Integer>> longestSubsequence = sequence.getLongestSubsequence();
        result.set(convertPairsListToString(longestSubsequence));
    }

    @DebugLog
    public void validatePairs(Editable value) {
        String username = value.toString();
        if (mValidator.isValid(username, FieldType.Pairs)) {
            isPairsValid = true;
            return;
        }
        isPairsValid = false;
    }

    private ArrayList<Pair<Integer, Integer>> parsePairs(String pairsString) {
        String[] values = pairsString.split(" ");
        ArrayList<Pair<Integer, Integer>> pairs = new ArrayList<>();
        try {
            for (int i = 0; i < values.length; i += 2) {
                Integer first = Integer.parseInt(values[i]);
                Integer second = Integer.parseInt(values[i + 1]);
                Pair<Integer, Integer> pair = new Pair<>(first, second);
                pairs.add(pair);
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return pairs;
    }

    private String convertPairsListToString(@NonNull ArrayList<Pair<Integer, Integer>> pairs) {
        StringBuilder builder = new StringBuilder();
        for (Pair<Integer, Integer> pair : pairs) {
            builder.append(pair.first)
                    .append(" ")
                    .append(pair.second)
                    .append(" ");
        }
        return builder.toString();
    }

    @DebugLog
    @Override
    public void saveInstanceState(@NonNull Bundle outState) {
        if (!TextUtils.isEmpty(value.get())) {
            outState.putString(KEY_VALUE, value.get());
        }
        if (!TextUtils.isEmpty(result.get())) {
            outState.putString(KEY_RESULT, result.get());
        }
    }

    @DebugLog
    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(KEY_VALUE)) {
            savedInstanceState.getString(KEY_VALUE, value.get());
        }
        if (savedInstanceState.containsKey(KEY_RESULT)) {
            savedInstanceState.getString(KEY_RESULT, result.get());
        }
    }
}
