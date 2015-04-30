/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.opus;

import javax.sound.midi.ShortMessage;
import origine_mundi.Integers;
import origine_mundi.MidiByte;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.BrevFactory.Shift;
import origine_mundi.ludior.ChordStroke;
import origine_mundi.ludior.Expression;
import origine_mundi.ludior.Expression.Command;
import origine_mundi.ludior.Expression.Control;
import origine_mundi.ludior.Ludior;
import origine_mundi.ludior.Tempus;
import origine_mundi.ludior.Tempus.Comes;
import origine_mundi.ludior.Tempus.Rapidus;
import origine_mundi.machine.MU500;

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
        return new Tempus(new Comes[]{}, new Rapidus[]{new Rapidus(0, 0, 100, true)});
    }

    @Override
    protected void callDevices(MidiMachines midi_machines) {
        midi_machines.put(0, MU500.instance(0));
    }

    @Override
    protected void sequence() {
        
        ChordStroke stroke1 = new ChordStroke(0.8, 1.0, 0.1, 0.1, true);
        ChordStroke stroke3 = new ChordStroke(0.8, 1.0, 0.025, 0.02, true);
        Expression exp1 = new Expression(
                new Control(0x01, 0),
                new Control(0x01, 0, 100, 2, 2.5),
                new Control(0x0a, 0),
                new Control(0x0a, 0, 127, 0, 3.5),
                new Command(ShortMessage.PITCH_BEND, 8192),
                new Command(ShortMessage.PITCH_BEND, 0, 8192, 0, 0.2)
        );
        Expression exp2 = new Expression(
                new Control(0x01, 0),
                new Control(0x01, 0, 100, 2, 2.5),
                new Control(0x0a, 127),
                new Control(0x0a, 127, 0, 0, 3.5),
                new Command(ShortMessage.PITCH_BEND, 8192),
                new Command(ShortMessage.PITCH_BEND, 0, 8192, 0, 0.1)
        );
        Expression exp3 = new Expression(
                new Control(0x0a, 64),
                new Control(0x01, 0),
                new Control(0x01, 0, 100, 2, 2.5),
                new Command(ShortMessage.PITCH_BEND, 6000, 8192, 0, 0.1)
        );
        BrevFactory bf1 = new BrevFactory(0, 0, 0, 0, 0d);
        bf1.program(1);
        brevs(bf1.remove());
        bf1.setLoco(0, 0d);
        bf1.note(new Integers(0, 1, 2), 100, 3.5, 1, stroke1, exp1, false);
        
        BrevFactory bf2 = new BrevFactory(1, 0, 1, 0, 0d);
        bf2.program(1);
        brevs(bf2.remove());
        bf2.setLoco(0, 0d);
        bf2.note(new Integers(0, 1, 2, 3, 4), 100, 3.5, 1, stroke1, exp2, true);
        
        BrevFactory bf3 = new BrevFactory(2, 0, 2, 0, 0d);
        bf3.program(28);
        brevs(bf3.remove());
        bf3.setLoco(0, 0d);
        bf3.note(new Integers(0, 1, 2, 3), 120, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(3, 2, 1, 0), 100, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(0, 1, 2, 3),  50, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(3, 2, 1, 0),  80, 0.25, 1, stroke3, exp3, true);
        
        bf3.note(new Integers(0, 1, 2, 3), 100, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(3, 2, 1, 0), 120, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(0, 1, 2, 3),  50, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(3, 2, 1, 0),  80, 0.25, 1, stroke3, exp3, true);
        
        bf3.note(new Integers(0, 1, 2, 3), 100, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(3, 2, 1, 0), 120, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(0, 1, 2, 3), 120, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(3, 2, 1, 0),  50, 0.25, 1, stroke3, exp3, true);
        
        bf3.note(new Integers(0, 1, 2, 3),  50, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(3, 2, 1, 0),  80, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(0, 1, 2, 3), 100, 0.25, 1, stroke3, exp3, true);
        bf3.note(new Integers(3, 2, 1, 0),  50, 0.25, 1, stroke3, exp3, true);
        
        
        Shift shift = new Shift();
        int[][] notes = new int[][]{
            new int[]{60, 64, 67}, 
            new int[]{62, 65, 69}, 
            new int[]{63, 67, 70}, 
            new int[]{65, 68, 72}};
        for(int i = 0;i < notes.length;i++){
            shift.loco(i + 1, 0);
            shift.putNote(0, notes[i][0]);
            shift.putNote(1, notes[i][1]);
            shift.putNote(2, notes[i][2]);
            shift.putNote(3, notes[i][0] + 12);
            shift.putNote(4, notes[i][1] + 12);
            brevs(bf1.shift(shift));
            brevs(bf2.shift(shift));
            brevs(bf3.shift(shift));
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
