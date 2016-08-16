package com.lonja.calculator.common.util;

import android.support.annotation.NonNull;
import android.util.Log;

public class FactorialHelper {

    public static int findFactorialDigitsSum(int value) {
        Log.e("find factorial thread", Thread.currentThread().getName());
        int buffer = 0;
        int[] factorial = findFactorial(value);
        for (int i : factorial) {
            buffer += i;
        }
        return buffer;
    }

    @NonNull
    public static int[] findFactorial(int value) {
        int[] buffer = {1};
        for (int i = value; i > 1; i--) {
            buffer = multiply(buffer, getDigitsFromInt(i));
        }
        return buffer;
    }

    @NonNull
    private static int[] getDigitsFromInt(int value) {
        int length = Integer.toString(value).length();
        int[] buffer = new int[length];
        int i = length - 1;
        while (value != 0) {
            buffer[i] = value % 10;
            value /= 10;
            i--;
        }
        return buffer;
    }

    @NonNull
    private static int[] multiply(@NonNull int[] val1, @NonNull int[] val2) {
        //length of result array may be @val1.length + @val2.length or @val1.length + @val2.length - 1
        //at first set the result's array length to @val1.length + @val2.length - 1
        //later length could be longer if result number won't fit into original length
        int[] result = new int[val1.length + val2.length - 1];
        //column multiplication without carrying-over digits in these cycles
        for (int i = 0; i < val1.length; i++) {
            for (int j = 0; j < val2.length; j++) {
                result[i + j] += val1[i] * val2[j];
            }
        }
        //carrying-over digits
        for (int i = result.length - 1; i >= 1; i--) {
            result[i - 1] += result[i] / 10;
            result[i] %= 10;
        }
        //array elongation if needs
        if (result[0] / 10 != 0) {
            int[] newArray = new int[result.length + 1];
            System.arraycopy(result, 0, newArray, 1, newArray.length - 1);
            newArray[0] += result[0] / 10;
            newArray[1] %= 10;
            result = newArray;
        }
        return result;
    }
}
