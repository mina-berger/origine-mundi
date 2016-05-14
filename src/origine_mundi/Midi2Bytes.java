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
public class Midi2Bytes extends Number implements Comparable<Midi2Bytes> {
    public static final Midi2Bytes MIN = new Midi2Bytes(0);
    public static final Midi2Bytes MAX = new Midi2Bytes((int)Math.pow(0x80, 2) - 1);
    private int value;
    private MidiByte msb;
    private MidiByte lsb;
    public Midi2Bytes(int value){
        if(value >= Math.pow(0x80, 2) || value < 0){
            throw new OmException("midi 2 bytes is out of range : " + value);
        }
        this.value = value;
        msb = new MidiByte(value / 0x80);
        lsb = new MidiByte(value % 0x80);
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

    public MidiByte msb(){
        return msb;
    }
    public MidiByte lsb(){
        return lsb;
    }

    @Override
    public int compareTo(Midi2Bytes o) {
        return Integer.compare(value, o.value);
    }
    @Override
    public boolean equals(Object o){
        if(o instanceof Midi2Bytes){
            return compareTo((Midi2Bytes)o) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + this.value;
        return hash;
    }


}
