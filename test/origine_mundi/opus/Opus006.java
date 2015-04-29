/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.BrevFactory.Shift;
import origine_mundi.ludior.Ludior;
import origine_mundi.ludior.Tempus;
import origine_mundi.ludior.Tempus.Comes;
import origine_mundi.ludior.Tempus.Rapidus;

/**
 *
 * @author Mina
 */
public class Opus006 extends Ludior {

    public Opus006() {
        super(true);
    }

    @Override
    protected Tempus getTempus() {
        return new Tempus(new Comes[]{}, new Rapidus[]{new Rapidus(0, 0, 120, true)});
    }

    @Override
    protected void callDevices(MidiMachines midi_machines) {
        //midi_machines.
    }

    @Override
    protected void sequence() {
        double p_ab = 0.0;
        double p_ad = 0.25;
        double m_ab = 2.0;
        double m_ad = 2.5;
        int mod = 100;
        int pitch = 8191;
        
        BrevFactory bf = new BrevFactory(0, 0, 0, 0, 0d);
        bf.program(10);
        brevs(bf.remove());
        bf.setLoco(1, 0d);
        bf.note(0, 100, 3.5, 1, false);
        bf.note(1, 100, 3.5, 1, false);
        bf.note(2, 100, 3.5, 1, false);
        bf.control(0x01, 0);
        bf.pitch(0);
        for(int i = 0;i < mod;i++){
            bf.setBeat(m_ab + (m_ad - m_ab) * (double)i / (double)mod);
            bf.control(0x01, i);
        }
        for(int i = 0;i < pitch;i++){
            bf.setBeat(p_ab + (p_ad - p_ab) * (double)i / (double)pitch);
            bf.pitch(i);
        }
        Shift shift = new Shift();
        int[][] notes = new int[][]{
            new int[]{60, 64, 67}, 
            new int[]{62, 65, 69}, 
            new int[]{63, 67, 70}, 
            new int[]{65, 68, 72}};
        for(int i = 0;i < notes.length;i++){
            shift.loco(i, 0);
            shift.putNote(0, notes[i][0]);
            shift.putNote(1, notes[i][1]);
            shift.putNote(2, notes[i][2]);
            brevs(bf.shift(shift));
        }
    }
    /*private void measure(int measure, int note0){
        double temp_ab = 2.0;
        double temp_ad = 3.5;
        int mod = 100;
        
        double head = 4 * measure + 1;
        note(0, note0, 100, head, 4);
        brev(ShortMessage.CONTROL_CHANGE, 0, 0x01,   0, head);
        brev(ShortMessage.PITCH_BEND, 0, 0x01,   0, head);
        for(int i = 0;i < mod;i++){
            double temp = head + temp_ab + (temp_ad - temp_ab) * (double)i / (double)mod;
            brev(ShortMessage.CONTROL_CHANGE, 0, 0x01,  i, temp);
            brev(ShortMessage.PITCH_BEND, 0, 0x01,  i, temp);
        }
    }*/
 
}
