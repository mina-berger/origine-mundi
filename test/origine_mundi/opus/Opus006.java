/*
 * Chord Stroke test
 * using PB but data is heavy
 */

package origine_mundi.opus;

import javax.sound.midi.ShortMessage;
import la.clamor.Talea;
import com.mina.util.Integers;
import origine_mundi.MidiMachines;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.BrevFactory.Shift;
import origine_mundi.ludior.ChordStroke;
import origine_mundi.ludior.Expression;
import origine_mundi.ludior.Expression.Command;
import origine_mundi.ludior.Expression.Control;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Ludior;
import origine_mundi.ludior.Tempus;
import origine_mundi.ludior.Tempus.Comes;
import origine_mundi.ludior.Tempus.Rapidus;
import origine_mundi.machine.D_110;
import origine_mundi.machine.MU500;
import origine_mundi.machine.TG77;
import origine_mundi.machine.U_110;

/**
 *
 * @author Mina
 */
public class Opus006 extends Ludior {

    public Opus006() {
        super(false);
    }

    @Override
    protected Tempus getTempus() {
        return new Tempus(new Comes[]{}, new Rapidus[]{new Rapidus(new Talea(), 100, true)});
    }

    @Override
    protected void callDevices(MidiMachines midi_machines) {
        midi_machines.put(0, TG77.instance());
        midi_machines.put(1, MU500.instance(0));
        midi_machines.put(2, U_110.instance());
        midi_machines.put(3, D_110.instance());
    }

    @Override
    protected void sequence() {
        
        ChordStroke stroke1 = new ChordStroke(0.8, 1.0, 0.1, 0.1, true);
        ChordStroke stroke3 = new ChordStroke(0.8, 1.0, 0.01, 0.015, true);
        ChordStroke stroke4 = new ChordStroke(1.0, 0.9, 0.008, 0.011, true);
        Expression exp1 = new Expression(
                new Control(0x01, 0),
                new Control(0x01, 0, 100, 2, 2.5),
                new Control(0x0a, 0),
                new Control(0x0a, 0, 127, 0, 3.5),
                //new Command(ShortMessage.PITCH_BEND, 8192),
                new Command(ShortMessage.PITCH_BEND, 7000, 8192, 0, 0.2)
        );
        Expression exp2 = new Expression(
                new Control(0x01, 0),
                new Control(0x01, 0, 100, 2, 2.5),
                new Control(0x0a, 127),
                new Control(0x0a, 127, 0, 0, 3.5),
                //new Command(ShortMessage.PITCH_BEND, 8192),
                new Command(ShortMessage.PITCH_BEND, 7000, 8192, 0, 0.1)
        );
        Expression exp3 = new Expression(
                new Control(0x01, 20),
                //n//ew Control(0x01, 0, 100, 2, 2.5)
                new Command(ShortMessage.PITCH_BEND, 8450, 8000, 0, 0.1)
        );
        Expression exp4 = new Expression(
                new Control(0x01, 10, 30, 0, 0.1),
                new Command(ShortMessage.PITCH_BEND, 8300, 8100, 0, 0.15)
        );
        BrevFactory bf1 = new BrevFactory(new Iunctum(0, 2, 0));
        bf1.program(0, 10);
        brevs(bf1.remove());
        bf1.setLoco(new Talea());
        bf1.note(0, new Integers(0, 1, 2), 100, 3.5, 1, stroke1, exp1, false);
        
        BrevFactory bf2 = new BrevFactory(new Iunctum(1, 3, 1));
        bf2.program(0, 0);
        brevs(bf2.remove());
        bf2.setLoco(new Talea());
        bf2.note(0, new Integers(0, 1, 2, 3, 4), 100, 3.5, 1, stroke1, exp2, true);
        
        BrevFactory bf3 = new BrevFactory(
                new Iunctum(2, 0, 0), new Iunctum(2, 0, 1), new Iunctum(2, 0, 2), 
                new Iunctum(2, 0, 3), new Iunctum(2, 0, 4), new Iunctum(2, 0, 5));
        bf3.program(1);
        bf3.control(0, 0x0a, 0, 0);
        bf3.control(1, 0x0a, 10, 0);
        bf3.control(2, 0x0a, 20, 0);
        bf3.control(3, 0x0a, 30, 0);
        bf3.control(4, 0x0a, 40, 0);
        bf3.control(5, 0x0a, 50, 0);
        
//bf3.control(0x0a, 18, 0);
        brevs(bf3.remove());
        bf3.setLoco(new Talea());
        Integers st_d = new Integers(0, 1, 2, 3, 4, 5);
        Integers st_u = new Integers(5, 4, 3, 2, 1, 0);
        Integers st_i_d = new Integers(0, 1, 2, 3, 4, 5);
        Integers st_i_u = new Integers(5, 4, 3, 2, 1, 0);
        bf3.note(st_i_d, st_d, 120, 0.25, 1, stroke3, exp3, true);
        bf3.note(st_i_u, st_u, 100, 0.25, 0.4, stroke3, exp3, true);
        bf3.note(st_i_d, st_d,  50, 0.25, 0.4, stroke3, exp3, true);
        bf3.note(st_i_u, st_u,  80, 0.25, 0.4, stroke3, exp3, true);
        
        bf3.note(st_i_d, st_d, 100, 0.25, 1, stroke3, exp3, true);
        bf3.note(st_i_u, st_u, 120, 0.25, 1, stroke3, exp3, true);
        bf3.note(st_i_d, st_d,  50, 0.25, 0.4, stroke3, exp3, true);
        bf3.note(st_i_u, st_u,  80, 0.25, 0.4, stroke3, exp3, true);
        
        bf3.note(st_i_d, st_d, 100, 0.25, 1, stroke3, exp3, true);
        bf3.note(st_i_u, st_u, 120, 0.25, 1, stroke3, exp3, true);
        bf3.note(st_i_d, st_d, 120, 0.25, 0.4, stroke3, exp3, true);
        bf3.note(st_i_u, st_u,  50, 0.25, 0.4, stroke3, exp3, true);
        
        bf3.note(st_i_d, st_d,  50, 0.25, 0.2, stroke3, exp3, true);
        bf3.note(st_i_u, st_u,  80, 0.25, 0.4, stroke3, exp3, true);
        bf3.note(st_i_d, st_d, 100, 0.25, 1, stroke3, exp3, true);
        bf3.note(st_i_u, st_u,  50, 0.25, 0.4, stroke3, exp3, true);
        
        BrevFactory bf4 = new BrevFactory(
                new Iunctum(3, 1, 0), new Iunctum(3, 1, 1), new Iunctum(3, 1, 2), 
                new Iunctum(3, 1, 3), new Iunctum(3, 1, 4), new Iunctum(3, 1, 5));
//                new Iunctum(3, 0, 8), new Iunctum(3, 0, 14), new Iunctum(3, 0, 10), 
//                new Iunctum(3, 0, 11), new Iunctum(3, 0, 12), new Iunctum(3, 0, 13));
        bf4.program(1);
        bf4.control(0, 0x0a, 127, 0);
        bf4.control(1, 0x0a, 117, 0);
        bf4.control(2, 0x0a, 107, 0);
        bf4.control(3, 0x0a, 97, 0);
        bf4.control(4, 0x0a, 87, 0);
        bf4.control(5, 0x0a, 77, 0);
        
        //bf4.control(0x0a, 110, 0);
        brevs(bf4.remove());
        bf4.setLoco(new Talea());
        bf4.note(st_i_d, st_d, 120, 0.25, 1, stroke4, exp4, true);
        bf4.note(st_i_u, st_u,  70, 0.25, 0.4, stroke4, exp4, true);
        bf4.note(st_i_d, st_d,  50, 0.25, 0.3, stroke4, exp4, true);
        bf4.note(st_i_u, st_u, 120, 0.25, 1, stroke4, exp4, true);
        
        bf4.note(st_i_d, st_d,  70, 0.25, 0.4, stroke4, exp4, true);
        bf4.note(st_i_u, st_u,  50, 0.25, 0.3, stroke4, exp4, true);
        bf4.note(st_i_d, st_d, 120, 0.25, 1, stroke4, exp4, true);
        bf4.note(st_i_u, st_u, 100, 0.25, 1, stroke4, exp4, true);
        
        bf4.note(st_i_d, st_d,  70, 0.25, 0.3, stroke4, exp4, true);
        bf4.note(st_i_u, st_u, 120, 0.25, 1, stroke4, exp4, true);
        bf4.note(st_i_d, st_d,  70, 0.25, 0.4, stroke4, exp4, true);
        bf4.note(st_i_u, st_u,  50, 0.25, 1, stroke4, exp4, true);
        
        bf4.note(st_i_d, st_d, 120, 0.25, 1, stroke4, exp4, true);
        bf4.note(st_i_u, st_u,  80, 0.25, 0.8, stroke4, exp4, true);
        bf4.note(st_i_d, st_d,  90, 0.25, 1, stroke4, exp4, true);
        bf4.note(st_i_u, st_u, 100, 0.25, 0.5, stroke4, exp4, true);

        
        Shift shift1 = new Shift();
        Shift shift2 = new Shift();
        int[][] notes = new int[][]{
            new int[]{57, 61, 64}, 
            new int[]{59, 62, 66}, 
            new int[]{60, 64, 67}, 
            new int[]{62, 65, 69}};
        int[][] chords = new int[][]{
            new int[]{-1, 45, 52, 56, 61, 64}, 
            new int[]{-1, 47, 54, 57, 62, 66},
            new int[]{-1, 48, 55, 59, 64, 67}, 
            new int[]{-1, 50, 57, 60, 65, 69}, 
        };
        for(int i = 0;i < 48;i++){
            shift1.loco(new Talea(i + 1, 0));
            shift1.putNote(0, notes[i % notes.length][0]);
            shift1.putNote(1, notes[i % notes.length][1]);
            shift1.putNote(2, notes[i % notes.length][2]);
            shift1.putNote(3, notes[i % notes.length][0] + 12);
            shift1.putNote(4, notes[i % notes.length][1] + 12);
            brevs(bf1.shift(shift1));
            brevs(bf2.shift(shift1));
            shift2.loco(new Talea(i + 1, 0));
            shift2.putNote(0, chords[i % chords.length][0]);
            shift2.putNote(1, chords[i % chords.length][1]);
            shift2.putNote(2, chords[i % chords.length][2]);
            shift2.putNote(3, chords[i % chords.length][3]);
            shift2.putNote(4, chords[i % chords.length][4]);
            shift2.putNote(5, chords[i % chords.length][5]);
            brevs(bf3.shift(shift2));
            brevs(bf4.shift(shift2));
        }
    }
 
}
