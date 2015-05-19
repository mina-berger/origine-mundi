/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.ludior;

import javax.sound.midi.ShortMessage;
import origine_mundi.MidiByte;

/**
 *
 * @author Mina
 */
public class Brev {
    private final Iunctum iunctum;
    //private final int track;
    //private final int device;
    private final int command;
    //private final int channel;
    private final MidiByte data1;
    private final MidiByte data2;
    private final int talea;
    private final double beat;
    public Brev(Iunctum iunctum, int command, MidiByte data1, MidiByte data2, double beat){
        this(iunctum, command, data1, data2, 0, beat);
    }
    public Brev(Iunctum iunctum, int command, MidiByte data1, MidiByte data2, int talea, double beat){
        this.iunctum = iunctum;
        this.command = command;
        this.data1 = data1;
        this.data2 = data2;
        this.talea = talea;
        this.beat = beat;
    }
    public Iunctum getIunctum() {
        return iunctum;
    }
    public int getCommand() {
        return command;
    }

    public MidiByte getData1() {
        return data1;
    }

    public MidiByte getData2() {
        return data2;
    }
    public int getTalea() {
        return talea;
    }
    public double getBeat() {
        return beat;
    }
    public static void main(String[] a){
        Brev brev = 
                new Brev(new Iunctum(0, 0, 0), ShortMessage.PROGRAM_CHANGE, 
                        new MidiByte(1), 
                        new MidiByte(0), 0, 0);
    }
    public boolean isNote(){
        return command == ShortMessage.NOTE_ON || command == ShortMessage.NOTE_OFF;
    }
}
