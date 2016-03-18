/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

import java.io.Serializable;
import java.math.BigDecimal;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author user
 */
public class Aestimatio implements Cloneable, Comparable<Aestimatio>, Serializable, Mergibilis<Aestimatio> {

    private double value;

    public Aestimatio() {
        this(0);
    }

    public Aestimatio(double aestimatio) {
        value = aestimatio;
    }

    //Aestimatio(double aestimatio, boolean is_raw) {
    //    value = aestimatio;
    //}

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
    
    public Aestimatio resto(Aestimatio partitor) {
        return new Aestimatio(value % partitor.value);
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
    public Aestimatio sqrt(){
        return new Aestimatio(FastMath.sqrt(value));
    }
    public Aestimatio pow(double y){
        return new Aestimatio(FastMath.pow(value, y));
    }
    public static Aestimatio atan2(Aestimatio ordinate, Aestimatio abscissa){
        return new Aestimatio(FastMath.atan2(ordinate.value, abscissa.value));
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
    public boolean equals(Aestimatio a){
        return compareTo(a) == 0;
    }

    //public double rawValue() {
    //    return doubleValue();
    //}

    @Override
    public Aestimatio mergo(long diff, long index, Aestimatio tectum) {
        return multiplico(new Aestimatio(diff - index)).addo(
            tectum.multiplico(new Aestimatio(index))).partior(new Aestimatio(diff));
    }
}
