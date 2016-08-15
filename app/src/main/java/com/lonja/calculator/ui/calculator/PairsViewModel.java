package com.lonja.calculator.ui.calculator;

import android.databinding.ObservableField;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Pair;

import com.lonja.calculator.common.IntegerPairSequence;
import com.lonja.calculator.ui.common.BaseViewModel;
import com.lonja.calculator.ui.common.navigator.Navigator;

import java.util.ArrayList;

import hugo.weaving.DebugLog;

public class PairsViewModel extends BaseViewModel<PairsFragment> {

    private static final String KEY_VALUE = "value";

    private static final String KEY_RESULT = "result";

    public final ObservableField<String> value = new ObservableField<>();

    public final ObservableField<String> result = new ObservableField<>();

    @DebugLog
    protected PairsViewModel(@NonNull Navigator navigator) {
        super(navigator);
    }

    @DebugLog
    public void calculate() {
        ArrayList<Pair<Integer, Integer>> pairs = parsePairs(value.get());
        IntegerPairSequence sequence = new IntegerPairSequence(pairs);
        ArrayList<Pair<Integer, Integer>> longestSubsequence = sequence.getLongestSubsequence();
        result.set(convertPairsListToString(longestSubsequence));
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
        outState.putString(KEY_RESULT, result.get());
        outState.putString(KEY_VALUE, value.get());
    }

    @DebugLog
    @Override
    public void restoreInstanceState(@NonNull Bundle savedInstanceState) {
        result.set(savedInstanceState.getString(KEY_RESULT));
        value.set(savedInstanceState.getString(KEY_VALUE));
    }
}
