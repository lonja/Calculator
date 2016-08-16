package com.lonja.calculator.common.bindings;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.view.View;
import android.widget.TextView;

import com.lonja.calculator.R;

import java.util.Objects;

import hugo.weaving.DebugLog;

public class Bindings {

    @DebugLog
    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    public static Integer getInteger(TextView textView) {
        String value = textView.getText().toString();
        return Objects.equals(value, "") ? null : Integer.parseInt(value);
    }

//    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
//    public static List<Pair<Integer, Integer>> getPairs(TextView textView) {
//        String[] values = textView.getText().toString().split(" ");
//        List<Pair<Integer, Integer>> pairs = new ArrayList<>();
//        for (int i = 0; i < values.length; i += 2) {
//            Integer first = Integer.parseInt(values[i]);
//            Integer second = Integer.parseInt(values[i + 1]);
//            Pair<Integer, Integer> pair = new Pair<>(first, second);
//            pairs.add(pair);
//        }
//        return pairs;
//    }
//
//    @BindingAdapter(value = "android:text")
//    public static void setPairs(TextView textView, List<Pair<Integer, Integer>> pairs) {
//
//    }

    @DebugLog
    @BindingAdapter(value = "android:text")
    public static void setInteger(TextView textView, Integer value) {
        if (value == null) {
            textView.setText("");
            return;
        }
        if (textView.getText().toString().equals(value.toString())) {
            return;
        }
        textView.setText(value.toString());
    }

    @BindingAdapter(value = "android:text")
    public static void setBoolean(TextView textView, Boolean value) {
        if (value == null) {
            textView.setText(null);
        } else if (value) {
            textView.setText(R.string.palindrome);
        } else {
            textView.setText(R.string.not_palindrome);
        }
    }

    @BindingAdapter(value = "android:enabled")
    public static void setEnabled(View view, Integer value) {
        if (value == null) {
            view.setEnabled(false);
            return;
        }
        view.setEnabled(true);
    }

    @BindingAdapter(value = "android:enabled")
    public static void setEnabled(View view, String value) {
        if (value == null || Objects.equals(value, "")) {
            view.setEnabled(false);
            return;
        }
        view.setEnabled(true);
    }
}