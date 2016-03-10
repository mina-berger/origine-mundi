/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.opus;

import java.util.ArrayList;
import la.clamor.Velocitas;
import la.clamor.Punctum;
import la.clamor.forma.CadentesFormae;
import la.clamor.forma.Distortion;
import la.clamor.forma.FIRFilter;
import la.clamor.opus.Mensa;
import la.clamor.opus.Taleae.Comes;
import la.clamor.opus.Taleae.Rapidus;
import origine_mundi.archive.ArchiveLudior;

/**
 *
 * @author minae.hiyamae
 */
public class Opus021 extends Mensa {

    int count = 8;

    public Opus021() {
        super(true, true);
    }

    @Override
    protected void ponoComitis(ArrayList<Comes> comites) {
        comites.add(new Comes(0, 4));
    }

    @Override
    protected void ponoRapidi(ArrayList<Rapidus> rapidi) {
        rapidi.add(new Rapidus(0, 0, 120, false));
        //rapidi.add(new Rapidus(count, 0, 600, true));
    }

    @Override
    public void initio() {
    }

    @Override
    protected void creo() {
        ponoInstrument(0, new Punctum(1, 0.), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new Distortion(new Punctum(0.8), new Punctum(2), new Punctum(1.2))));
        ponoInstrument(1, new Punctum(0., 1), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new Distortion(new Punctum(0.8), new Punctum(2), new Punctum(1.2))));
        ponoInstrument(2, new Punctum(0.5, 0.5), new ArchiveLudior("mu500r", "drum_basic", 10), new CadentesFormae(
            new FIRFilter(500, 7000, true)));
        ponoPan(0, count, 0, new Punctum(0., 1));
        ponoPan(1, count, 0, new Punctum(1, 0.));

        for (int i = 0; i < count; i++) {
            ludo(0, i, 0.0, 0.5, 36, Velocitas.una(1));
            ludo(1, i, 1.0, 0.6, 40, Velocitas.una(1));
            ludo(0, i, 2.0, 0.5, 36, Velocitas.una(1));
            ludo(0, i, 2.5, 0.6, 36, Velocitas.una(0.8));
            ludo(1, i, 3.0, 0.7, 40, Velocitas.una(1));

            ludo(2, i, 0.5, 0.5, 42, Velocitas.una(1));
            ludo(2, i, 1.5, 0.45, 46, Velocitas.una(1));
            ludo(2, i, 1.95, 0.05, 44, Velocitas.una(0.1));
            ludo(2, i, 2.5, 0.5, 42, Velocitas.una(1));
            ludo(2, i, 3.5, 0.45, 46, Velocitas.una(1));
            ludo(2, i, 3.95, 0.05, 44, Velocitas.una(0.1));
            /*ludo(0, i, 0.5, 0.5, 69,  Velocitas.una(1));
            ludo(0, i, 1.0, 0.5, 72, Velocitas.una(1));
            ludo(0, i, 1.5, 0.5, 76, Velocitas.una(1));
            ludo(0, i, 2.0, 0.5, 79, Velocitas.una(1));
            ludo(0, i, 2.5, 0.5, 76, Velocitas.una(1));
            ludo(0, i, 3.0, 0.5, 72, Velocitas.una(1));
            ludo(0, i, 3.5, 0.5, 69,  Velocitas.una(1));*/
        }
    }

    @Override
    protected void anteFacio() {
    }

}
