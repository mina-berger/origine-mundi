/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import com.mina.util.Integers;
import java.util.ArrayList;
import java.util.TreeMap;
import la.clamor.Cinctum;
import la.clamor.Vel;
import la.clamor.Punctum;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Chorus;
import la.clamor.forma.Delay;
import la.clamor.forma.Distortion;
import la.clamor.forma.FIRFilter;
import la.clamor.opus.Humanizer;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae.Comes;
import la.clamor.opus.Taleae.Rapidus;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author minae.hiyamae
 */
public class Opus021 extends Mensa {

    int count = 32;

    public Opus021() {
        super(true, true);
    }

    @Override
    protected void ponoComitis(ArrayList<Comes> comites) {
        comites.add(new Comes(0, 4));
    }

    @Override
    protected void ponoRapidi(ArrayList<Rapidus> rapidi) {
        rapidi.add(new Rapidus(0, 0, 100, false));
        //rapidi.add(new Rapidus(count, 0, 600, true));
    }

    @Override
    public void initio() {
    }

    @Override
    protected void creo() {
        ponoInstrument(0, new Punctum(1), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new Distortion(new Punctum(0.8), new Punctum(2), new Punctum(1.2))));
        ponoInstrument(1, new Punctum(1), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new Distortion(new Punctum(0.8), new Punctum(2), new Punctum(1.2))));
        ponoInstrument(2, new Punctum(0.5, 0.5), new Cinctum(), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new FIRFilter(500, 7000, true)));
        ponoInstrument(10, new Punctum(0.6), new Cinctum(), new ArchiveLudior("mu500r", "piano", 500), new CadentesFormae(
            new Chorus(new Punctum(0.1), new Punctum(10), new Punctum(1, 0), new Punctum(0, 1)),
            new Delay(new Punctum(Delay.temps(100, 0.5)), new Punctum(3), new Punctum(0.5, -0.5)),
            new FIRFilter(500, 7000, true)));
        ponoInstrument(11, new Punctum(1), new Cinctum(), new ArchiveLudior("mu500r", "bass006433", 50), new CadentesFormae(
            new Distortion(new Punctum(0.8), new Punctum(2), new Punctum(1.2)),
            new Chorus(new Punctum(0.08), new Punctum(5.5), new Punctum(0.5), new Punctum(0.5)),
            new FIRFilter(500, 6000, true)));
        ponoHumanizer(new Humanizer()
            .pono(0, 0, 1).pono(0.25, -0.02, 0.5).pono(0.5, -0.02, 0.5).pono(0.75, -0.02, 0.8)
            .ponoRandomVelocitas(0, 0.5), 0, 1, 2, 10, 11);
        
        TreeMap<Integer, Integers> notes = new TreeMap<>();
        notes.put(0, new Integers(4, 11, 16, 4, 11, 14, 19, 9));
        notes.put(4, new Integers(0, 7, 14, 4, 10, 14, 19, 9));
        notes.put(8, new Integers(4, 11, 16, 4, 11, 14, 19, 9));
        notes.put(12, new Integers(0, 7, 14, 4, 10, 14, 19, 9));
        notes.put(16, new Integers(4, 11, 16, 4, 11, 14, 19, 9));
        notes.put(20, new Integers(0, 7, 14, 4, 10, 14, 19, 9));
        notes.put(24, new Integers(4, 11, 16, 4, 11, 14, 19, 9));
        notes.put(28, new Integers(0, 7, 14, 4, 10, 14, 19, 9));

        for (int i = 0; i < count; i++) {
            if(i % 2 == 0){
                ponoPan(0, i, 0, new Cinctum(true, new Punctum(0), new Punctum(1)));
                ponoPan(0, i + 1, 3.99, new Cinctum(true, new Punctum(1), new Punctum(0)));
                ponoPan(1, i, 0, new Cinctum(true, new Punctum(1), new Punctum(0)));
                ponoPan(1, i + 1, 3.99, new Cinctum(true, new Punctum(0), new Punctum(1)));
                ponoPan(11, i, 0, new Cinctum(true, new Punctum(1), new Punctum(0)));
                ponoPan(11, i + 1, 0, new Cinctum(true, new Punctum(0), new Punctum(1)));
                ponoPan(11, i + 1, 3.99, new Cinctum(true, new Punctum(1), new Punctum(0)));
            }
            ludo(0, i, 0.0, 0.5, 36, new Vel(1));
            ludo(0, i, 0.75, 0.25, 36, new Vel(1));
            ludo(1, i, 1.0, 0.6, 40, new Vel(1));
            ludo(0, i, 1.25, 0.25, 36, new Vel(0.5));
            ludo(0, i, 1.5, 0.5, 36, new Vel(1));
            ludo(0, i, 2.5, 0.5, 36, new Vel(1));
            ludo(1, i, 3.0, 0.7, 40, new Vel(1));
            ludo(0, i, 3.5, 0.5, 36, new Vel(0.8));

            /*ludo(2, i, 0, 0.5, 42, new Velocitas(1));
            ludo(2, i, 0.5, 0.5, 42, new Velocitas(1));
            ludo(2, i, 1.5, 0.25, 46, new Velocitas(1));
            ludo(2, i, 1.75, 0.25, 44, new Velocitas(0.5));
            ludo(2, i, 1, 0.5, 42, new Velocitas(1));
            ludo(2, i, 2, 0.5, 42, new Velocitas(1));
            ludo(2, i, 2.5, 0.5, 42, new Velocitas(1));
            ludo(2, i, 3, 0.5, 42, new Velocitas(1));
            ludo(2, i, 3.5, 0.25, 46, new Velocitas(1));
            ludo(2, i, 3.75, 0.25, 44, new Velocitas(0.5));
            */

            for (int j = 0; j < 16; j++) {
                ludo(2, i, j * 0.25, 0.25, j == 3 || j == 7?46:42, new Vel(1));
            }
            Integers note = notes.floorEntry(i).getValue();
            int base10 = 60;
            ludo(10, i, 0, 0.75, base10 + note.get(3), new Vel(0.8));
            ludo(10, i, 0.75, 0.75, base10 + note.get(4), new Vel(0.8));
            ludo(10, i, 1.5, 0.75, base10 + note.get(5), new Vel(0.8));
            ludo(10, i, 2.25, 0.75, base10 + note.get(6), new Vel(0.8));
            ludo(10, i, 3, 0.75, base10 + note.get(7), new Vel(0.8));
            
            int base11 = 36;
            ludo(11, i, 0, 0.75, base11 + note.get(0), new Vel(0.8));
            ludo(11, i, 0.75, 0.75, base11 + note.get(1), new Vel(0.8));
            ludo(11, i, 1.5, 2, base11 + note.get(2), new Vel(0.8));
            ludo(11, i, 3.5, 0.3, base11 + note.get(1), new Vel(0.8));

            /*ludo(0, i, 0.5, 0.5, 69,  Velocitas.una(1));
            ludo(0, i, 1.0, 0.5, 72, Velocitas.una(1));
            ludo(0, i, 1.5, 0.5, 76, Velocitas.una(1));
            ludo(0, i, 2.0, 0.5, 79, Velocitas.una(1));
            ludo(0, i, 2.5, 0.5, 76, Velocitas.una(1));
            ludo(0, i, 3.0, 0.5, 72, Velocitas.una(1));
            ludo(0, i, 3.5, 0.5, 69,  Velocitas.una(1));*/
        }
        ponoLevel(0, 0, 0, new Punctum(0));
        ponoLevel(2, 0, 0, new Punctum(0));
        ponoLevel(11, 0, 0, new Punctum(0));
        ponoLevel(0, 7, 3.49, new Punctum(0));
        ponoLevel(2, 7, 3.49, new Punctum(0));
        ponoLevel(11, 7, 3.49, new Punctum(0));
        ponoLevel(0, 7, 3.5, new Punctum(1));
        ponoLevel(2, 7, 3.5, new Punctum(1));
        ponoLevel(11, 7, 3.5, new Punctum(1));
        
        ponoMasterLevel(26, 0, new Punctum(1));
        ponoMasterLevel(32, 0, new Punctum(0));
    }

    @Override
    protected void anteFacio() {
    }

}
