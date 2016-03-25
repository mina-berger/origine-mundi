/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor;

/**
 *
 * @author user
 */
public class Talea implements Comparable<Talea> {

    private int talea;
    private double beat;

    public Talea() {
        this(0, 0);
    }

    public int getTalea() {
        return talea;
    }

    public double getBeat() {
        return beat;
    }
    public Talea(int talea, double beat) {
        this.talea = talea;
        this.beat = beat;
    }
    public Talea shiftBeat(double shift){
        return new Talea(talea, beat + shift);
    }
    public Talea shift(Talea shift){
        return new Talea(talea + shift.talea, beat + shift.beat);
    }

    @Override
    public String toString() {
        return talea + ":" + beat;
    }

    @Override
    public int compareTo(Talea o) {
        int compare = Integer.compare(talea, o.talea);
        if (compare != 0) {
            return compare;
        }
        return Double.compare(beat, o.beat);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Talea) {
            return false;
        }
        return compareTo((Talea) o) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.talea;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.beat) ^ (Double.doubleToLongBits(this.beat) >>> 32));
        return hash;
    }

}
