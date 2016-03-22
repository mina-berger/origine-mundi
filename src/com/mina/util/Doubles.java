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
public class Doubles extends ArrayList<Double> {

    public Doubles(double d) {
        add(d);
    }

    public Doubles(double... ds) {
        addAll(Arrays.asList(ArrayUtils.toObject(ds)));
    }

    public Doubles(List<Double> ds) {
        addAll(ds);
    }

    public double[] toPrimitiveArray() {
        return ArrayUtils.toPrimitive(toArray(new Double[]{}));
    }

    public Doubles append(double d) {
        add(d);
        return this;
    }

    public Doubles append(double... ds) {
        addAll(Arrays.asList(ArrayUtils.toObject(ds)));
        return this;
    }

    public Doubles append(List<Double> ds) {
        addAll(ds);
        return this;
    }

    public static Doubles fillValue(double value, int length) {
        Doubles ds = new Doubles();
        while (ds.size() < length) {
            ds.append(value);
        }
        return ds;
    }

}
