package com.lonja.calculator.common;

import android.support.annotation.NonNull;
import android.util.Pair;

import java.util.ArrayList;
import java.util.Random;

public class IntegerPairSequence {

    private ArrayList<Pair<Integer, Integer>> mCollection;

    public IntegerPairSequence() {
        mCollection = new ArrayList<>();
    }

    public IntegerPairSequence(@NonNull ArrayList<Pair<Integer, Integer>> collection) {
        mCollection = collection;
    }

    public static ArrayList<Pair<Integer, Integer>> generateCollection(int collectionSize, int minValue, int maxValue) {
        Random random = new Random();
        ArrayList<Pair<Integer, Integer>> list = new ArrayList<>();
        for (int i = 0; i < collectionSize; i++) {
            list.add(new Pair<>(random.nextInt((maxValue - minValue) + 1) + minValue,
                    random.nextInt((maxValue - minValue) + 1) + minValue)
            );
        }
        return list;
    }

    public ArrayList<Pair<Integer, Integer>> getSequence() {
        return mCollection;
    }

    public void setSequence(@NonNull ArrayList<Pair<Integer, Integer>> sequence) {
        mCollection = sequence;
    }

    public void addItem(Pair<Integer, Integer> item) {
        if (mCollection == null) {
            mCollection = new ArrayList<>();
        }
        mCollection.add(item);
    }

    public ArrayList<Pair<Integer, Integer>> getLongestSubsequence() {
        ArrayList<Pair<Integer, Integer>> longestSequence = new ArrayList<>();
        for (int i = 0; i < mCollection.size(); i++) {
            Pair<Integer, Integer> prevValue = mCollection.get(i);
            //temporary sequence for collecting items
            ArrayList<Pair<Integer, Integer>> tmpSequence = new ArrayList<>();
            tmpSequence.add(prevValue);
            for (int j = i + 1; j < mCollection.size(); j++) {
                Pair<Integer, Integer> curValue = mCollection.get(j);
                //if current item does not satisfy then nested cycle breaks
                if (prevValue.first > curValue.first || prevValue.second < curValue.second) {
                    break;
                }
                //moving pointer to the current item
                tmpSequence.add(curValue);
                prevValue = curValue;
            }
            if (tmpSequence.size() < longestSequence.size()) {
                continue;
            }
            longestSequence = tmpSequence;
        }
        return longestSequence;
    }
}
