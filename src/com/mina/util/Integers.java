/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;

/**
 *
 * @author user
 */
public class Integers extends ArrayList<Integer> {

    //boolean termine = false;
    public Integers(int integer) {
        add(integer);
    }

    public Integers(int... ints) {
        addAll(Arrays.asList(ArrayUtils.toObject(ints)));
    }

    public Integers(List<Integer> ints) {
        addAll(ints);
    }

    public static Integers getSequence(int start, int end, boolean includes_end) {
        Integers is = new Integers();
        for (int i = start; i < end; i++) {
            is.add(i);
        }
        if (includes_end) {
            is.add(end);
        }
        return is;
    }

    public int[] toPrimitiveArray() {
        return ArrayUtils.toPrimitive(toArray(new Integer[]{}));
    }

    public Integers append(int integer) {
        add(integer);
        return this;
    }

    public Integers append(int... ints) {
        addAll(Arrays.asList(ArrayUtils.toObject(ints)));
        return this;
    }

    public Integers append(List<Integer> ints) {
        addAll(ints);
        return this;
    }

    public static Integers fillValue(int value, int length) {
        Integers ints = new Integers();
        while (ints.size() < length) {
            ints.append(value);
        }
        return ints;
    }

}
