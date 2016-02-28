/*
 * Chord Stroke test
 * using PB but data is heavy
 */

package origine_mundi.opus;

import la.clamor.Talea;
import com.mina.util.Integers;
import origine_mundi.MidiMachines;
import origine_mundi.ludior.BrevFactory;
import origine_mundi.ludior.BrevFactory.Shift;
import origine_mundi.ludior.ChordStroke;
import origine_mundi.ludior.Expression;
import origine_mundi.ludior.Expression.Control;
import origine_mundi.ludior.Iunctum;
import origine_mundi.ludior.Ludior;
import origine_mundi.ludior.Tempus;
import origine_mundi.ludior.Tempus.Comes;
import origine_mundi.ludior.Tempus.Rapidus;
import origine_mundi.machine.D_110;

/**
 *
 * @author Mina
 */
public class Opus007 extends Ludior {

    public Opus007() {
        super(false);
    }

    @Override
    protected Tempus getTempus() {
        return new Tempus(new Comes[]{}, new Rapidus[]{new Rapidus(new Talea(), 100, true)});
    }

    @Override
    protected void callDevices(MidiMachines midi_machines) {
        D_110 d_110 = D_110.instance();
        midi_machines.put(0, D_110.instance());
        /*int tone = 2;
        d_110.callMemoryTone(tone, 0);
        d_110.callMemoryTone(tone, 1);
        d_110.callMemoryTone(tone, 2);
        d_110.callMemoryTone(tone, 3);
        d_110.callMemoryTone(tone, 4);
        d_110.callMemoryTone(tone, 5);
        int basssynth = 36;*/
    }

    @Override
    protected void sequence() {
        
        ChordStroke stroke0 = new ChordStroke(0.8, 1.0, 0.01, 0.015, true);
        Expression exp0 = new Expression(
                new Control(0x01, 10, 30, 0, 0.1)//,
                //new Command(ShortMessage.PITCH_BEND, 8300, 8100, 0, 0.15)
        );
        BrevFactory bf0 = new BrevFactory(
                new Iunctum(2, 0, 0), new Iunctum(2, 0, 1), new Iunctum(2, 0, 2), 
                new Iunctum(2, 0, 3), new Iunctum(2, 0, 4), new Iunctum(2, 0, 5));
        bf0.program(6);
        bf0.control(0, 0x0a, 0, 0);
        bf0.control(1, 0x0a, 10, 0);
        bf0.control(2, 0x0a, 20, 0);
        bf0.control(3, 0x0a, 30, 0);
        bf0.control(4, 0x0a, 40, 0);
        bf0.control(5, 0x0a, 50, 0);
        
        brevs(bf0.remove());
        bf0.setLoco(new Talea());
        Integers st_d = new Integers(0, 1, 2, 3, 4, 5);
        Integers st_u = new Integers(5, 4, 3, 2, 1, 0);
        Integers st_i_d = new Integers(0, 1, 2, 3, 4, 5);
        Integers st_i_u = new Integers(5, 4, 3, 2, 1, 0);
        bf0.note(st_i_d, st_d, 120, 0.25, 1, stroke0, exp0, true);
        bf0.note(st_i_u, st_u, 100, 0.25, 0.4, stroke0, exp0, true);
        bf0.note(st_i_d, st_d,  50, 0.25, 0.4, stroke0, exp0, true);
        bf0.note(st_i_u, st_u,  80, 0.25, 0.4, stroke0, exp0, true);
        
        bf0.note(st_i_d, st_d, 100, 0.25, 1, stroke0, exp0, true);
        bf0.note(st_i_u, st_u, 120, 0.25, 1, stroke0, exp0, true);
        bf0.note(st_i_d, st_d,  50, 0.25, 0.4, stroke0, exp0, true);
        bf0.note(st_i_u, st_u,  80, 0.25, 0.4, stroke0, exp0, true);
        
        bf0.note(st_i_d, st_d, 100, 0.25, 1, stroke0, exp0, true);
        bf0.note(st_i_u, st_u, 120, 0.25, 1, stroke0, exp0, true);
        bf0.note(st_i_d, st_d, 120, 0.25, 0.4, stroke0, exp0, true);
        bf0.note(st_i_u, st_u,  50, 0.25, 0.4, stroke0, exp0, true);
        
        bf0.note(st_i_d, st_d,  50, 0.25, 0.2, stroke0, exp0, true);
        bf0.note(st_i_u, st_u,  80, 0.25, 0.4, stroke0, exp0, true);
        bf0.note(st_i_d, st_d, 100, 0.25, 1, stroke0, exp0, true);
        bf0.note(st_i_u, st_u,  50, 0.25, 0.4, stroke0, exp0, true);
        

        
        Shift shift0 = new Shift();
        int[][] chords = new int[][]{
            new int[]{-1, 45, 52, 56, 61, 64}, 
            new int[]{-1, 47, 54, 57, 62, 66},
            new int[]{-1, 48, 55, 59, 64, 67}, 
            new int[]{-1, 50, 57, 60, 65, 69}, 
        };
        for(int i = 0;i < 8;i++){
            shift0.loco(new Talea(i + 1, 0));
            shift0.putNote(0, chords[i % chords.length][0]);
            shift0.putNote(1, chords[i % chords.length][1]);
            shift0.putNote(2, chords[i % chords.length][2]);
            shift0.putNote(3, chords[i % chords.length][3]);
            shift0.putNote(4, chords[i % chords.length][4]);
            shift0.putNote(5, chords[i % chords.length][5]);
            brevs(bf0.shift(shift0));
        }
    }
 
}
