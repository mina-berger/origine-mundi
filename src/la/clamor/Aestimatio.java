/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author user
 */
public class Aestimatio implements Cloneable, Comparable<Aestimatio>, Serializable {

    private double value;

    public Aestimatio() {
        this(0);
    }

    public Aestimatio(double aestimatio) {
        this(aestimatio, false);
    }

    Aestimatio(double aestimatio, boolean is_raw) {
        value = aestimatio;
    }

    Aestimatio(BigDecimal value) {
        this.value = value.doubleValue();
    }

    public double doubleValue() {
        return value;
    }

    public long longValue() {
        return Math.round(value);
    }

    public int intValue() {
        return new Long(longValue()).intValue();
    }

    public Aestimatio partior(Aestimatio partitor) {
        return new Aestimatio(value / partitor.value);
    }

    public Aestimatio multiplico(Aestimatio multiplicator) {
        return new Aestimatio(value * multiplicator.value);
    }

    public Aestimatio addo(Aestimatio multiplicator) {
        return new Aestimatio(value + multiplicator.value);
    }

    public Aestimatio subtraho(Aestimatio multiplicator) {
        return new Aestimatio(value - multiplicator.value);
    }

    public Aestimatio nego() {
        return new Aestimatio(-value);
    }

    public Aestimatio max(Aestimatio aestimatio) {
        return new Aestimatio(FastMath.max(value, aestimatio.value));
    }

    public Aestimatio min(Aestimatio aestimatio) {
        return new Aestimatio(FastMath.min(value, aestimatio.value));
    }

    public Aestimatio abs() {
        return new Aestimatio(FastMath.abs(value));
    }

    @Override
    public String toString() {
        return Functiones.toString(value);
        //return Double.toString(value);
    }

    @Override
    public int compareTo(Aestimatio a) {
        return Double.compare(value, a.value);
    }

    public double rawValue() {
        return doubleValue();
    }
}
