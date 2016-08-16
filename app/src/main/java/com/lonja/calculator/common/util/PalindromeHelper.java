package com.lonja.calculator.common.util;

public class PalindromeHelper {

    private static final int MIN_VAL = 100;

    private static final int MAX_VAL = 999;

    public static int find() {
        int maxPalindrome = 0;
        for (int i = MAX_VAL; i >= MIN_VAL; i--) {
            for (int j = MAX_VAL; j >= MIN_VAL; j--) {
                int product = i * j;
                if (!isPalindrome(product) || !(maxPalindrome < product)) {
                    continue;
                }
                maxPalindrome = product;
            }
        }
        return maxPalindrome;
    }

    public static boolean isPalindrome(int value) {
        //checking equality of number and reversed number
        return value == Integer.parseInt(new StringBuilder(Integer.toString(value))
                .reverse()
                .toString()
        );
    }
}
