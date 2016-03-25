/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi;

/**
 *
 * @author user
 */
public class MidiByte extends Number implements Comparable<MidiByte> {
    public static final MidiByte MIN = new MidiByte(0);
    public static final MidiByte MAX = new MidiByte(127);
    private int value;
    public MidiByte(int value){
        if(value >= 128 || value < 0){
            throw new OmException("midi byte is out of range : " + value);
        }
        this.value = value;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public int compareTo(MidiByte o) {
        return Integer.compare(value, o.value);
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof MidiByte){
            return compareTo((MidiByte)o) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.value;
        return hash;
    }
    //public static void main(String[] args){
    //    new MidiByte(127);
    //}
}
