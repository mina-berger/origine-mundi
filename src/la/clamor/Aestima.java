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
public class Aestima implements Cloneable, Comparable<Aestima>, Serializable, Mergibilis<Aestima> {

    private double value;

    public Aestima() {
        this(0);
    }

    public Aestima(double aestimatio) {
        value = aestimatio;
    }

    //Aestimatio(double aestimatio, boolean is_raw) {
    //    value = aestimatio;
    //}

    Aestima(BigDecimal value) {
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

    @Override
    public Aestima partior(Aestima partitor) {
        return new Aestima(value / partitor.value);
    }
    
   public Aestima resto(Aestima partitor) {
        return new Aestima(value % partitor.value);
    }

    @Override
    public Aestima multiplico(Aestima multiplicator) {
        return new Aestima(value * multiplicator.value);
    }

    @Override
    public Aestima addo(Aestima multiplicator) {
        return new Aestima(value + multiplicator.value);
    }

    @Override
    public Aestima subtraho(Aestima multiplicator) {
        return new Aestima(value - multiplicator.value);
    }

    @Override
    public Aestima nego() {
        return new Aestima(-value);
    }

    public Aestima max(Aestima aestimatio) {
        return new Aestima(FastMath.max(value, aestimatio.value));
    }

    public Aestima min(Aestima aestimatio) {
        return new Aestima(FastMath.min(value, aestimatio.value));
    }

    public Aestima abs() {
        return new Aestima(FastMath.abs(value));
    }
    public Aestima sqrt(){
        return new Aestima(FastMath.sqrt(value));
    }
    public Aestima pow(double y){
        return new Aestima(FastMath.pow(value, y));
    }
    public static Aestima atan2(Aestima ordinate, Aestima abscissa){
        return new Aestima(FastMath.atan2(ordinate.value, abscissa.value));
    }
            

    @Override
    public String toString() {
        return Functiones.toString(value);
        //return Double.toString(value);
    }

    @Override
    public int compareTo(Aestima a) {
        return Double.compare(value, a.value);
    }
    public boolean equals(Aestima a){
        return compareTo(a) == 0;
    }

    //public double rawValue() {
    //    return doubleValue();
    //}

    @Override
    public Aestima mergo(long diff, long index, Aestima tectum) {
        return multiplico(new Aestima(diff - index)).addo(tectum.multiplico(new Aestima(index))).partior(new Aestima(diff));
    }
}
